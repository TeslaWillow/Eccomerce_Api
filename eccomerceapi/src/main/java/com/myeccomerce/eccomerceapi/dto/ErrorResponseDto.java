// ./eccomerceapi/src/main/java/com/myeccomerce/api/dto/ErrorResponseDto.java
package com.myeccomerce.eccomerceapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}