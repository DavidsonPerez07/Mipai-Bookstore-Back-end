package com.udea.edyl.EDyL.web.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto implements Serializable {
    private Long addressId;
    private String street;
    private String city;
    private String postalCode;
}
