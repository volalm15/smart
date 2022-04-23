package com.vollify.smart.device.model.dto;


import com.vollify.smart.device.model.Location;
import com.vollify.smart.device.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeviceDto {
    String id;

    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    @NotBlank
    @NotNull
    String name;

    @NotNull
    @Valid
    Location location;

    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    @NotBlank
    @NotNull
    String area;

    @NotNull
    Type type;

}
