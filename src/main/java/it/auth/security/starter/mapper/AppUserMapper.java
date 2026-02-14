// AppUserMapper.java
package it.auth.security.starter.mapper;

import it.auth.security.core.dto.AppUserDTO;
import it.auth.security.core.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserMapper INSTANCE = Mappers.getMapper(AppUserMapper.class);

    AppUserDTO toDto(AppUser appUser);

    AppUser toEntity(AppUserDTO appUserDTO);

    List<AppUserDTO> toDto(List<AppUser> appUser);

    List<AppUser> toEntity(List<AppUserDTO> appUserDTO);
}
