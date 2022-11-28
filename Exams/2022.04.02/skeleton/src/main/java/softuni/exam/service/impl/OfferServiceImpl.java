package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
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
        return null;
    }

    @Override
    public String exportOffers() {
        return null;
    }
}
