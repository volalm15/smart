package com.vollify.smart.controller.model.dto;

import com.vollify.smart.controller.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDto {
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
