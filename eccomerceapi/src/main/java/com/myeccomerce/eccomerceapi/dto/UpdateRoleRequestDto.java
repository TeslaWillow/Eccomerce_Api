package com.myeccomerce.eccomerceapi.dto;

import com.myeccomerce.eccomerceapi.model.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequestDto {
    @NotNull(message = "Role is required")
    private Role role;
}
