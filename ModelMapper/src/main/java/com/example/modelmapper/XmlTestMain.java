package com.example.modelmapper;

import com.example.modelmapper.entities.dtos.AddressXmlDto;
import com.example.modelmapper.entities.dtos.CountryXlmDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Component
public class XmlTestMain implements CommandLineRunner {
    private final JAXBContext addressContext;
    private final JAXBContext countryContext;

    public XmlTestMain(JAXBContext addressContext, JAXBContext countryContext) {
        this.addressContext = addressContext;
        this.countryContext = countryContext;
    }

    @Override
    public void run(String... args) throws Exception {
        CountryXlmDto countryXlmDto = new CountryXlmDto("Bulgaria");
        AddressXmlDto xmlDto = new AddressXmlDto(5, "Bulgaria", "Gabrovo");

//        JAXBContext context = JAXBContext.newInstance(AddressXmlDto.class);

        Marshaller countryMarshaller = countryContext.createMarshaller();
        countryMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        countryMarshaller.marshal(countryXlmDto, System.out);

        Marshaller addressMarshaller = addressContext.createMarshaller();
        addressMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        addressMarshaller.marshal(xmlDto, System.out);


//        Unmarshaller unmarshaller = addressContext.createUnmarshaller();
//
//        AddressXmlDto addressXmlDto = (AddressXmlDto) unmarshaller.unmarshal(System.in);
//<?xml version="1.0" encoding="UTF-8" standalone="yes"?><addressXmlDto><id>5</id><country>Bulgaria</country><city>Gabrovo</city></addressXmlDto>
//        System.out.println(addressXmlDto);
    }
}
