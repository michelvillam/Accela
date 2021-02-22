package com.accela.personaddress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Integer id;

    @Size(max=50, message = "The max length of the street is 50 characters.")
    @NotNull
    @NotBlank
    private String street;

    @Size(max=30, message = "The max length of the city is 30 characters.")
    @NotNull
    @NotBlank
    private String city;

    @Size(max=30, message = "The max length of the street is 30 characters.")
    @NotNull
    @NotBlank
    private String state;

    @Size(max=10, message = "The max length of the street is 10 characters.")
    @NotNull
    @NotBlank
    private String postalCode;

    private Set<AddressDto> addresses;
}
