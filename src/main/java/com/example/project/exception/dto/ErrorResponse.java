package com.example.project.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ErrorResponse {
    private int code;
    private String message;
}
