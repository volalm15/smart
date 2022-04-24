package com.vollify.smart.controller.model.dto;


import com.vollify.smart.controller.model.Location;
import com.vollify.smart.controller.model.Property;
import com.vollify.smart.controller.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    String id;

    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    @NotBlank
    @NotNull
    String name;

    @NotNull
    Type type;

    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    @NotBlank
    @NotNull
    String area;

    @NotBlank
    @NotNull
    String topic;

    @NotNull
    @Valid
    Location location;

    @NotNull
    List<Property> properties = new ArrayList<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime lastModification;
}
