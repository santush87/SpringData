package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.config.Messages;
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

import static softuni.exam.config.Messages.INVALID_COUNTRY;

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

        final List<CountryImportDTO> countries = Arrays.stream(gson.fromJson(readCountriesFromFile(), CountryImportDTO[].class)).toList();

        for (CountryImportDTO country : countries) {
            boolean isValid = this.validationUtils.isValid(country);

            if (this.countryRepository.findFirstByCountryName(country.getCountryName()).isPresent()) {
                continue;
            }
            if (isValid) {
                builder.append(String.format(Messages.VALID_COUNTRY_FORMAT,
                        country.getCountryName(),
                        country.getCurrency())).append(System.lineSeparator());

                this.countryRepository.saveAndFlush(this.mapper.map(country, Country.class));

            } else {
                builder.append(INVALID_COUNTRY).append(System.lineSeparator());
            }

        }
        return builder.toString();
    }


//                .filter(countryDTO -> {
//                            boolean isValid = this.validationUtils.isValid(countryDTO);
//
//                            if (this.countryRepository.findFirstByCountryName(countryDTO.getCountryName()).isPresent()) {
//                                isValid = false;
//                            }
//                            if (isValid) {
//                                builder.append(String.format(VALID_COUNTRY_FORMAT,
//                                        countryDTO.getCountryName(),
//                                        countryDTO.getCurrency()));
//
//                                this.countryRepository.saveAndFlush(this.mapper.map(countryDTO, Country.class));
//
//                            } else {
//                                builder.append(INVALID_COUNTRY);
//                            }
//                            return isValid;
//                        })
//                    .map(countryImportDTO -> this.mapper.map(countryImportDTO, Country.class))
//                .toList();


}
