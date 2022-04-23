package com.vollify.smart.device.repository;


import com.vollify.smart.device.model.Attribute;
import com.vollify.smart.device.model.Device;
import com.vollify.smart.device.model.Type;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AttributeRepository extends MongoRepository<Attribute, String> {
    Optional<Attribute> findAttributeByName(String name);
}
