package com.example.modelmapper;

import com.example.modelmapper.entities.Address;
import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.entities.dtos.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
public class ModelMapperMain implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        Address address = new Address("Bulgaria", "Sevlievo");
        Employee employee = new Employee("Martin", BigDecimal.TEN, address);

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        System.out.println(employeeDto);
    }
}
