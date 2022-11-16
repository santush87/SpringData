package com.example.modelmapper.config;

import com.example.modelmapper.entities.dtos.AddressXmlDto;
import com.example.modelmapper.entities.dtos.CountryXlmDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Scanner;

@Configuration
public class Config {

    @Bean
    public ModelMapper createModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Scanner createScanner(){
        return new Scanner(System.in);
    }

    @Bean("addressContext")
    public JAXBContext createAddressContext() throws JAXBException {
        return JAXBContext.newInstance(AddressXmlDto.class);
    }

    @Bean("countryContext")
    public JAXBContext createCountryContext() throws JAXBException {
        return JAXBContext.newInstance(CountryXlmDto.class);
    }
}
