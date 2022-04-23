package com.vollify.smart.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    String id;

    @Pattern(regexp = "^[A-Za-z\s]+$")
    @NotBlank
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
