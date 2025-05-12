package com.ecom.ExpressEcom.mapper;

import com.ecom.ExpressEcom.dto.CredentialsDTO;
import com.ecom.ExpressEcom.entity.Credentials;
import com.ecom.ExpressEcom.entity.RoleBasedAuthority;

public interface CredentialsMapper {
    public static Credentials toEntity(CredentialsDTO dto) {
        if (dto == null) return null;
        return Credentials.builder()
              //  .credentialsId(dto.getCredentialsId())
                .userName(dto.getUserName())
                .pazzword(dto.getPazzword())
                .roleBasedAuthority(RoleBasedAuthority.valueOf(dto.getRole()))
                .build();
    }

    public static CredentialsDTO toDTO(Credentials entity) {
        if (entity == null) return null;
        return CredentialsDTO.builder()
               // .credentialsId(entity.getCredentialsId())
                .userName(entity.getUserName())
                .pazzword(entity.getPazzword())
                .role(entity.getRoleBasedAuthority().name())
                .build();
    }
}
