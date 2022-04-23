package com.vollify.smart.device.model.dto;


import com.vollify.smart.device.model.Attribute;
import com.vollify.smart.device.model.Location;
import com.vollify.smart.device.model.State;
import com.vollify.smart.device.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    String id;

    @Pattern(regexp = "^[A-Za-z\s]+$")
    @NotBlank
    @NotNull
    String name;

    @NotNull
    Type type;

    @Pattern(regexp = "^[A-Za-z\s]+$")
    @NotBlank
    @NotNull
    String room;

    @NotBlank
    @NotNull
    String topic;

    @NotNull
    @Valid
    Location location;

    @NotNull
    List<State> states = new ArrayList<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime lastModification;
}
