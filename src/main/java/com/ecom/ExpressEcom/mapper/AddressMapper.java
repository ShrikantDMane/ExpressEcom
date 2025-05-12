package com.ecom.ExpressEcom.mapper;

import com.ecom.ExpressEcom.dto.AddressDTO;
import com.ecom.ExpressEcom.entity.Address;

public interface AddressMapper {

    public static Address toAddressEntity(AddressDTO addressDTO){

        if(addressDTO==null) return null;

         return Address.builder()
                .streetName(addressDTO.getStreetName())
                .city(addressDTO.getStreetName())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .build();


    }
    public static AddressDTO toAddressDTO(Address addressEntity){

        if(addressEntity==null) return null;

         return AddressDTO.builder()
                 .streetName(addressEntity.getStreetName())
                 .city(addressEntity.getCity())
                 .state(addressEntity.getState())
                 .zipCode(addressEntity.getZipCode())
                 .build();
    }



}
