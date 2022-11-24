package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.config.Messages.INVALID_CITY;
import static softuni.exam.config.Messages.VALID_CITY_FORMAT;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper mapper, Gson gson, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/cities.json"));
    }

    @Override
    public String importCities() throws IOException {
        final StringBuilder builder = new StringBuilder();

        final List<CityImportDTO> cities = Arrays.stream(gson.fromJson(readCitiesFileContent(), CityImportDTO[].class)).toList();
        for (CityImportDTO city : cities) {
            boolean isValid = this.validationUtils.isValid(city);

            if (this.cityRepository.findFirstByCityName(city.getCityName()).isPresent()) {
                continue;
            }

            if (isValid) {
                builder.append(String.format(VALID_CITY_FORMAT,
                        city.getCityName(),
                        city.getPopulation())).append(System.lineSeparator());

                if (this.countryRepository.findById(city.getCountry()).isPresent()) {
                    City cityToSave = this.mapper.map(city, City.class);

                    cityToSave.setCountry(this.countryRepository.findById(city.getCountry()).get());

                    this.cityRepository.save(cityToSave);

                } else {

                    builder.append("Error").append(System.lineSeparator());
                }
            } else {
                builder.append(INVALID_CITY).append(System.lineSeparator());
            }

        }
        return builder.toString();
    }
//        Arrays.stream(gson.fromJson(readCitiesFileContent(), CityImportDTO[].class))
//                .map(dto -> {
//                    boolean isValid = this.validationUtils.isValid(dto);
//
//                    if (this.cityRepository.findFirstByCityName(dto.getCityName()).isPresent()) {
//                        isValid = false;
//                    }
//
//                    if (isValid) {
//                        builder.append(String.format(VALID_CITY_FORMAT,
//                                dto.getCityName(),
//                                dto.getPopulation()));
//
//                        if (this.countryRepository.getById(dto.getCountry()).isPresent()) {
//                            City cityToSave = this.mapper.map(dto, City.class);
//                            cityToSave.setCountry(this.countryRepository.getById(dto.getCountry()).get());
//                        }
//                    } else {
//                        builder.append(INVALID_CITY);
//                    }
//
//
//                });


}
