package com.accela.personaddress.controller;

import com.accela.personaddress.dto.AddressDto;
import com.accela.personaddress.service.AddressService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{userId}/address")
    public void addAddress(@RequestBody @Valid AddressDto addressDto, @PathVariable("userId") int userId){

        addressService.saveAddress(addressDto, userId);
    }

    @PutMapping("/address")
    public void updateAddress(@RequestBody @Valid AddressDto addressDto){

        addressService.updateAddress(addressDto);
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddress(@PathVariable("id") int id){

        addressService.deleteAddress(id);
    }

}
