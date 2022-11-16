package com.example.modelmapper;

import com.example.modelmapper.entities.dtos.AddressDTO;
import com.example.modelmapper.entities.dtos.CreateEmployeeDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

//@Component
public class JsonTestMain implements CommandLineRunner {
    /*
    {
    "firstName": "Martin",
    "salary": 5000,
    "address": {
        "country": "Bulgaria",
        "city": "Sevlievo"
        }
    }
     */

    private final Scanner scanner;

    public JsonTestMain(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        AddressDTO address1 = new AddressDTO("Bulgaria", "Sevlievo");
        AddressDTO address2 = new AddressDTO("Bulgaria", "Sofia");
        AddressDTO address3 = new AddressDTO("Bulgaria", "Veliko Tyrnovo");
        AddressDTO address4 = new AddressDTO("Bulgaria", "Varna");

        List<AddressDTO> addressList = List.of(address1, address2, address3, address4);

//        String json = gson.toJson(address1);
//        System.out.println(json);

        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO(
                "Martin", "Aleksandrov", BigDecimal.valueOf(5000), LocalDate.now(), address1);

        String json = gson.toJson(createEmployeeDTO);

        System.out.println(gson.toJson(addressList));

//        String input = this.scanner.nextLine();
//        CreateEmployeeDTO parsedDTO = gson.fromJson(input, CreateEmployeeDTO.class);


    }
}
