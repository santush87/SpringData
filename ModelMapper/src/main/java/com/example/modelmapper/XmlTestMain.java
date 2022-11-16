package com.example.modelmapper;

import com.example.modelmapper.entities.dtos.AddressXmlDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Component
public class XmlTestMain implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        AddressXmlDto xmlDto = new AddressXmlDto(5, "Bulgaria", "Gabrovo");

        JAXBContext context = JAXBContext.newInstance(AddressXmlDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(xmlDto, System.out);
    }
}
