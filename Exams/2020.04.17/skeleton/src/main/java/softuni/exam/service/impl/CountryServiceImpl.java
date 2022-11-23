package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static softuni.exam.config.Messages.INVALID_COUNTRY;
import static softuni.exam.config.Messages.VALID_COUNTRY_FORMAT;

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
        return Files.readString(Path.of("src/main/resources/files/json/countries.json"));
    }

    @Override
    public String importCountries() throws IOException {
        final StringBuilder builder = new StringBuilder();

        final List<Country> countries = Arrays.stream(gson.fromJson(readCountriesFromFile(), CountryImportDTO[].class))
                .filter(countryImportDTO -> {
                            boolean isValid = this.validationUtils.isValid(countryImportDTO);

                            if (isValid) {
                                builder.append(String.format(VALID_COUNTRY_FORMAT,
                                        countryImportDTO.getCountryName(),
                                        countryImportDTO.getCurrency()));
                            } else {
                                builder.append(INVALID_COUNTRY);
                            }
                            return isValid;
                        }
                ).map(countryImportDTO -> this.mapper.map(countryImportDTO, Country.class))
                .toList();

        this.countryRepository.saveAllAndFlush(countries);
        return builder.toString();
    }
}
