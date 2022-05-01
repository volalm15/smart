package com.vollify.smart.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RequestPayloadDto {
    @NotNull
    @NotBlank
    String content;
}
