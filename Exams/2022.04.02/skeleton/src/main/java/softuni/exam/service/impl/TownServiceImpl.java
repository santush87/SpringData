package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final Gson gson;
    private final ModelMapper mapper;

    public TownServiceImpl(TownRepository townRepository, ValidationUtils validationUtils, Gson gson, ModelMapper mapper) {
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of("D:\\SoftUni\\SpringData\\Exams\\2022.04.02\\skeleton\\src\\main\\resources\\files\\json\\towns.json"));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<TownImportDTO> importDTOS = Arrays.stream(gson.fromJson(readTownsFileContent(), TownImportDTO[].class)).toList();

        for (TownImportDTO dto : importDTOS) {
            boolean isValid = this.validationUtils.isValid(dto);

            if (isValid) {
                Optional<Town> optTownName = this.townRepository.findByTownName(dto.getTownName());

                if (optTownName.isPresent()) {
                    builder.append("Invalid town").append(System.lineSeparator());
                } else {
                    Town townToSave = this.mapper.map(dto, Town.class);
                    townToSave.setTownName(dto.getTownName());
                    townToSave.setPopulation(dto.getPopulation());

                    this.townRepository.save(townToSave);

                    builder.append(String.format("Successfully imported town %s - %d",
                                    dto.getTownName(),
                                    dto.getPopulation()))
                            .append(System.lineSeparator());
                }
            }else {
                builder.append("Invalid town").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
