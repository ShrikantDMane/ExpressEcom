package com.ecom.ExpressEcom.mapper;

import com.ecom.ExpressEcom.dto.AddressDTO;
import com.ecom.ExpressEcom.dto.UserDTO;
import com.ecom.ExpressEcom.entity.Address;
import com.ecom.ExpressEcom.entity.Credentials;
import com.ecom.ExpressEcom.entity.User;


import java.util.Set;
import java.util.stream.Collectors;

public interface UserMapper {

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        // Convert AddressDTOs to Address Entities
        Set<Address> addresses = null;
        if (dto.getAddressDTOSet() != null) {
            addresses = dto.getAddressDTOSet().stream()
                    .map(AddressMapper::toAddressEntity)
                    .collect(Collectors.toSet());
        }

        User user = User.builder()
                .firstName(dto.getFirstName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .projectName(dto.getProjectName())
                .address(addresses)
                .build();

        // Assign user to each address entity
        if (addresses != null) {
            addresses.forEach(address -> address.setUser(user));
        }

        // Convert CredentialsDTO to Credentials Entity
        Credentials credentials = CredentialsMapper.toEntity(dto.getCredentialsDTO());
        if (credentials != null) {
            credentials.setUser(user);
            user.setCredentials(credentials);
        }

        return user;
    }

    public static UserDTO toDTO(User entity) {
        if (entity == null) return null;

        Set<AddressDTO> addressDTOs = null;
        if (entity.getAddress() != null) {
            addressDTOs = entity.getAddress().stream()
                    .map(AddressMapper::toAddressDTO)
                    .collect(Collectors.toSet());
        }

        return UserDTO.builder()
                .firstName(entity.getFirstName())
                .email(entity.getEmail())
                .department(entity.getDepartment())
                .projectName(entity.getProjectName())
                .addressDTOSet(addressDTOs)
                .credentialsDTO(CredentialsMapper.toDTO(entity.getCredentials()))
                .build();
    }
}
