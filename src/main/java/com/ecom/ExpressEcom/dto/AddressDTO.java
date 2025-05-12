package com.ecom.ExpressEcom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class AddressDTO {

    @NotBlank(message = "Street name is required")
    @Size(max = 100, message = "Street name must be less than 100 characters")
    private String streetName;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must be less than 50 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State must be less than 50 characters")
    private String state;

    private int zipCode;
}
