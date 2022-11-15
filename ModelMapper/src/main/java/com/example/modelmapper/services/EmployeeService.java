package com.example.modelmapper.services;

import com.example.modelmapper.entities.Employee;
import com.example.modelmapper.entities.dtos.CreateEmployeeDTO;
import com.example.modelmapper.entities.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    Employee create(CreateEmployeeDTO employeeDTO);

    List<EmployeeDto> findAll();
}
