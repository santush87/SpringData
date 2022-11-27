package exam.service.impl;

import exam.model.Town;
import exam.model.dtos.TownImportDTO;
import exam.model.dtos.TownRootImportDTO;
import exam.repository.TownRepository;
import exam.service.TownService;
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
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtils validationUtils, ModelMapper mapper, XmlParser xmlParser) {
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/towns.xml"));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        StringBuilder builder = new StringBuilder();

        final File file = Path.of("src/main/resources/files/xml/towns.xml").toFile();
        TownRootImportDTO townRootImportDTO = xmlParser.fromFile(file, TownRootImportDTO.class);

        final List<TownImportDTO> dtos = townRootImportDTO.getTowns();

        for (TownImportDTO dto : dtos) {
            boolean isValid = validationUtils.isValid(dto);

            if (isValid) {
                Optional<Town> optTown = this.townRepository.findByName(dto.getName());

                if (optTown.isPresent()) {
                    builder.append("Invalid town").append(System.lineSeparator());
                } else {

                    Town town = this.mapper.map(optTown, Town.class);

                    town.setName(dto.getName());
                    town.setPopulation(dto.getPopulation());
                    town.setTravelGuide(dto.getTravelGuide());

                    this.townRepository.save(town);

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
