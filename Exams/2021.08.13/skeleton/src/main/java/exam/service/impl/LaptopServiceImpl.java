package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Laptop;
import exam.model.Shop;
import exam.model.dtos.LaptopImportDTO;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository, Gson gson, ValidationUtils validationUtils, ModelMapper mapper) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/laptops.json"));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder builder = new StringBuilder();

        List<LaptopImportDTO> importDTOS = Arrays.stream(this.gson.fromJson(readLaptopsFileContent(), LaptopImportDTO[].class)).toList();

        for (LaptopImportDTO dto : importDTOS) {
            boolean isValid = this.validationUtils.isValid(dto);

            if (isValid) {
                Optional<Laptop> optMacAddress = this.laptopRepository
                        .findByMacAddress(dto.getMacAddress());

                if (optMacAddress.isPresent()) {
                    builder.append("Invalid Laptop").append(System.lineSeparator());
                }else {
                    Laptop laptopToSave = this.mapper.map(dto, Laptop.class);
                    Optional<Shop> shop = this.shopRepository.findByName(dto.getShop().getName());

                    laptopToSave.setMacAddress(dto.getMacAddress());
                    laptopToSave.setCpuSpeed(dto.getCpuSpeed());
                    laptopToSave.setRam(dto.getRam());
                    laptopToSave.setStorage(dto.getStorage());
                    laptopToSave.setDescription(dto.getDescription());
                    laptopToSave.setPrice(dto.getPrice());
                    laptopToSave.setWarrantyType(dto.getWarrantyType());
                    laptopToSave.setShop(shop.get());

                    this.laptopRepository.save(laptopToSave);

                    builder.append(String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                    dto.getMacAddress(),
                                    dto.getCpuSpeed(),
                                    dto.getRam(),
                                    dto.getStorage()))
                            .append(System.lineSeparator());
                }
            } else {
                builder.append("Invalid Laptop").append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    @Override
    public String exportBestLaptops() {
        return null;
    }
}
