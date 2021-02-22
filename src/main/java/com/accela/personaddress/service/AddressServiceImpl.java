package com.accela.personaddress.service;

import com.accela.personaddress.domain.Address;
import com.accela.personaddress.dto.AddressDto;
import com.accela.personaddress.exception.AddressNotFoundException;
import com.accela.personaddress.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private Mapper mapper;

    public AddressServiceImpl(AddressRepository addressRepository, Mapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveAddress(AddressDto addressDto, int personId) {

        log.info("Saving address: {} into personId: {}", addressDto, personId);
        Address address = mapper.map(addressDto, Address.class);

        address.setPersonId(personId);

        addressRepository.save(address);
    }

    @Override
    public void updateAddress(AddressDto addressDto) {

        log.info("Updating address: {}", addressDto);

        Address address = addressRepository.findById(addressDto.getId()).orElseThrow(() -> {
            log.error("Address not found with id: {}", addressDto.getId());
            throw new AddressNotFoundException();
        });

        address.setCity(addressDto.getCity());
        address.setPostalCode(addressDto.getPostalCode());
        address.setState(addressDto.getState());
        address.setStreet(addressDto.getStreet());

        addressRepository.save(address);

    }

    @Override
    public void deleteAddress(int id) {

        log.info("Deleting address with id: {}", id);
        Address address = findAddressById(id);
        addressRepository.delete(address);
    }

    private Address findAddressById(int id){

        log.debug("Finding address by id: {}", id);
        return addressRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("Address not found by id: {}", id);
                    throw new AddressNotFoundException();
                });
    }
}
