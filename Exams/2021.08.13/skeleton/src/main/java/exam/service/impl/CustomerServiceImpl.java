package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Customer;
import exam.model.Town;
import exam.model.dtos.CustomerImportDTO;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, Gson gson, ValidationUtils validationUtils, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/customers.json"));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder builder = new StringBuilder();

        final List<CustomerImportDTO> importDTO =
                Arrays.stream(gson.fromJson(readCustomersFileContent(), CustomerImportDTO[].class)).toList();

        for (CustomerImportDTO dto : importDTO) {
            boolean isValid = this.validationUtils.isValid(dto);

            if (isValid) {
                Optional<Customer> optCustomer = this.customerRepository.findByEmail(dto.getEmail());

                if (optCustomer.isPresent()) {
                    builder.append("Invalid Customer").append(System.lineSeparator());
                } else {
                    Customer customerToSave = this.mapper.map(dto, Customer.class);
                    Optional<Town> town = this.townRepository.findByName(dto.getTown().getName());

                    customerToSave.setTown(town.get());

                    customerToSave.setRegisteredOn(LocalDate.parse(dto.getRegisteredOn(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                    this.customerRepository.save(customerToSave);

                    builder.append(String.format("Successfully imported Customer %s %s - %s",
                                    dto.getFirstName(),
                                    dto.getLastName(),
                                    dto.getEmail()))
                            .append(System.lineSeparator());
                }
            }else {
                builder.append("Invalid Customer").append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
