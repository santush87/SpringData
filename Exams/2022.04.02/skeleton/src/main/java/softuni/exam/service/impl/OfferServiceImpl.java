package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDTO;
import softuni.exam.models.dto.OfferRootImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final AgentService agentService;
    private final ApartmentRepository apartmentRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;

    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, AgentService agentService, ApartmentRepository apartmentRepository, XmlParser xmlParser, ValidationUtils validationUtils, ModelMapper mapper) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.agentService = agentService;
        this.apartmentRepository = apartmentRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of("D:\\SoftUni\\SpringData\\Exams\\2022.04.02\\skeleton\\src\\main\\resources\\files\\xml\\offers.xml"));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();

        final File file = Path.of("D:\\SoftUni\\SpringData\\Exams\\2022.04.02\\skeleton\\src\\main\\resources\\files\\xml\\offers.xml").toFile();
        final OfferRootImportDTO importDTO =
                xmlParser.fromFile(file, OfferRootImportDTO.class);
        final List<OfferImportDTO> dtos = importDTO.getOffers();

        for (OfferImportDTO dto : dtos) {
            boolean isValid = this.validationUtils.isValid(dto);

            if (isValid) {
                Agent agent = this.agentService.getAgentByName(dto.getAgent().getName());
                if (agent == null){
                    builder.append("Invalid offer").append(System.lineSeparator());

                } else {
                    Optional<Apartment> apartment = this.apartmentRepository.findById(dto.getApartment().getId());
                    Offer offerToSave = this.mapper.map(dto, Offer.class);
//                    offerToSave.setPrice(dto.getPrice());
                    offerToSave.setApartment(apartment.get());
//                    offerToSave.setPublishedOn(LocalDate.parse(dto.getPublishedOn(),
//                            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    offerToSave.setAgent(agent);

                    this.offerRepository.save(offerToSave);

                    builder.append(String.format("Successfully imported offer %.2f",
                                    dto.getPrice()))
                            .append(System.lineSeparator());
                }
            } else {
                builder.append("Invalid offer").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder builder = new StringBuilder();


        return builder.toString();
    }
}
