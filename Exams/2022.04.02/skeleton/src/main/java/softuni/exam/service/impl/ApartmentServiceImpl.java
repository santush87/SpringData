package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDTO;
import softuni.exam.models.dto.ApartmentRootImportDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, XmlParser xmlParser, ValidationUtils validationUtils, ModelMapper mapper) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of("D:\\SoftUni\\SpringData\\Exams\\2022.04.02\\skeleton\\src\\main\\resources\\files\\xml\\apartments.xml"));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        final File file = Path.of("D:\\SoftUni\\SpringData\\Exams\\2022.04.02\\skeleton\\src\\main\\resources\\files\\xml\\apartments.xml").toFile();

        ApartmentRootImportDTO importDTO = xmlParser.fromFile(file, ApartmentRootImportDTO.class);
        List<ApartmentImportDTO> dtos = importDTO.getApartments();

        for (ApartmentImportDTO dto : dtos) {
            boolean isValid = this.validationUtils.isValid(dto);

            if (isValid) {
                Optional<Apartment> optArea = this.apartmentRepository.findByArea(dto.getArea());
                Optional<Town> town = this.townRepository.findByTownName(dto.getTown());

                if (town.isPresent() && optArea.isPresent()) {
                    builder.append("Invalid apartment").append(System.lineSeparator());

                } else {
                    Apartment apartmentToSave = this.mapper.map(dto, Apartment.class);

                    apartmentToSave.setApartmentType(dto.getApartmentType());
                    apartmentToSave.setArea(dto.getArea());
                    apartmentToSave.setTown(town.get());

                    this.apartmentRepository.save(apartmentToSave);

                    builder.append(String.format("Successfully imported apartment %s - %.2f",
                                    dto.getApartmentType(),
                                    dto.getArea()))
                            .append(System.lineSeparator());
                }
            } else {
                builder.append("Invalid apartment").append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
