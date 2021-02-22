package com.accela.personaddress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    private Integer id;

    @Size(max=50, message = "The max length of the first name is 50 characters.")
    @NotNull
    @NotBlank
    private String firstName;

    @Size(max=50, message = "The max length of the last name is 50 characters.")
    @NotNull
    @NotBlank
    private String lastName;
}
