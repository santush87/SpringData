package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static softuni.exam.config.Messages.INVALID_COUNTRY;
import static softuni.exam.config.Messages.VALID_COUNTRY_FORMAT;
import static softuni.exam.config.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;

    public CountryServiceImpl(CountryRepository countryRepository,
                              Gson gson,
                              ValidationUtils validationUtils,
                              ModelMapper mapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(COUNTRIES_PATH);    }

    @Override
    public String importCountries() throws IOException {

        final StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readCountriesFromFile(), CountryImportDto[].class))
                .forEach(countryDto -> {
                    boolean isValid = this.validationUtils.isValid(countryDto);

                    if (this.countryRepository.findFirstByCountryName(countryDto.getCountryName()).isPresent()) {
                        isValid = false;
                    }

                    if (isValid) {
                        stringBuilder.append(String.format(VALID_COUNTRY_FORMAT,
                                countryDto.getCountryName(),
                                countryDto.getCurrency())).append(System.lineSeparator());

                        this.countryRepository.saveAndFlush(this.mapper.map(countryDto, Country.class));
                    } else {
                        stringBuilder.append(INVALID_COUNTRY).append(System.lineSeparator());
                    }
                });

        return stringBuilder.toString();
    }
}