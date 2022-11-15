package com.example.modelmapper.services;

import com.example.modelmapper.entities.Address;
import com.example.modelmapper.entities.dtos.AddressDTO;
import com.example.modelmapper.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public Address create(AddressDTO data) {
        ModelMapper modelMapper = new ModelMapper();

        Address address = modelMapper.map(data, Address.class);

        return this.addressRepository.save(address);
    }
}
