package com.vollify.smart.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(value = "attribute")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {
    @Id
    String id;

    @Indexed(unique = true)
    @NotNull
    @NotBlank
    String name;

    @NotNull
    List<Type> types = new ArrayList<>();

    @NotNull
    List<String> commands = new ArrayList<>();
}
