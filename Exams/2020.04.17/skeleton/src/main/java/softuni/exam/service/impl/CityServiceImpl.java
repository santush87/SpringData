package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import java.io.IOException;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return null;
    }

    @Override
    public String importCities() throws IOException {
        return null;
    }
}
