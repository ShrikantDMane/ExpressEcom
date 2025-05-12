package com.ecom.ExpressEcom.mapper;

import com.ecom.ExpressEcom.dto.CredentialsDTO;
import com.ecom.ExpressEcom.entity.Credentials;
import com.ecom.ExpressEcom.entity.RoleBasedAuthority;

public interface CredentialsMapper {
    public static Credentials toEntity(CredentialsDTO dto) {
        if (dto == null) return null;

        // Map the role string to the appropriate enum constant
        RoleBasedAuthority role;
        if (dto.getRole() == null) {
            // Default to ROLE_USER if no role provided
            role = RoleBasedAuthority.ROLE_USER;
        } else if (dto.getRole().startsWith("ROLE_")) {
            // If already prefixed with ROLE_, use as is
            role = RoleBasedAuthority.valueOf(dto.getRole());
        } else {
            // If not prefixed (e.g., "USER"), add the prefix
            role = RoleBasedAuthority.valueOf("ROLE_" + dto.getRole());
        }

        return Credentials.builder()
                .userName(dto.getUserName())
                .pazzword(dto.getPazzword())
                .roleBasedAuthority(role)
                .build();
    }

    public static CredentialsDTO toDTO(Credentials entity) {
        if (entity == null) return null;

        // For DTO, return just the role part without the "ROLE_" prefix
        String roleString = entity.getRoleBasedAuthority().name();
        String simplifiedRole = roleString.startsWith("ROLE_") ?
                roleString.substring(5) : roleString;

        return CredentialsDTO.builder()
                .userName(entity.getUserName())
                .pazzword(entity.getPazzword())
                .role(simplifiedRole)
                .build();
    }
}
