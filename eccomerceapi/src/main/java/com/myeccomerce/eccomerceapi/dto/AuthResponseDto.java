// ./eccomerceapi/src/main/java/com/myeccomerce/eccomerceapi/dto/AuthResponseDto.java
package com.myeccomerce.eccomerceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private Long id;
    private String email;
    private String role;
}