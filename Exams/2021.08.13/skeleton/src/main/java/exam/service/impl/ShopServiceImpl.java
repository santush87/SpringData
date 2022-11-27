package exam.service.impl;

import exam.model.Shop;
import exam.model.Town;
import exam.model.dtos.ShopImportDTO;
import exam.model.dtos.ShopRootImportDTO;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtils;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository, ValidationUtils validationUtils, ModelMapper mapper, XmlParser xmlParser) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/shops.xml"));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder builder = new StringBuilder();

        final File file = Path.of("src/main/resources/files/xml/shops.xml").toFile();
        ShopRootImportDTO shopRootImportDTO = xmlParser.fromFile(file, ShopRootImportDTO.class);

        final List<ShopImportDTO> dtos = shopRootImportDTO.getShops();

        for (ShopImportDTO dto : dtos) {
            boolean isValid = validationUtils.isValid(dto);

            if (isValid) {
                Optional<Shop> optShop = this.shopRepository.findByName(dto.getName());

                if (optShop.isPresent()) {
                    builder.append("Invalid shop").append(System.lineSeparator());
                } else {

                    Shop shop = this.mapper.map(optShop, Shop.class);
                    Optional<Town> town = this.townRepository.findByName(dto.getTown().getName());

                    shop.setName(dto.getName());
                    shop.setShopArea(dto.getShopArea());
                    shop.setIncome(dto.getIncome());
                    shop.setAddress(dto.getAddress());
                    shop.setEmployeeCount(dto.getEmployeeCount());
                    shop.setTown(town.get());

                    this.shopRepository.save(shop);

                    builder.append(String.format("Successfully imported Town Moscow",
                                    dto.getName()))
                            .append(System.lineSeparator());
                }
            } else {
                builder.append("Invalid town").append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
