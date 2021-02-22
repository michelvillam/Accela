package com.accela.personaddress.service;

import com.accela.personaddress.dto.AddressDto;

public interface AddressService {

    void saveAddress(AddressDto addressDto, int userId);

    void updateAddress(AddressDto addressDto);

    void deleteAddress(int id);
}
