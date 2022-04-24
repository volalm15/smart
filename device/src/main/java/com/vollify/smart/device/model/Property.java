package com.vollify.smart.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    @DBRef
    Attribute attribute;
    List<Payload> payload;
}
