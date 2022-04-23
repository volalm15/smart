package com.vollify.smart.device.service;

import com.vollify.smart.device.model.Attribute;
import com.vollify.smart.device.model.Device;
import com.vollify.smart.device.model.Type;
import com.vollify.smart.device.repository.AttributeRepository;
import com.vollify.smart.device.repository.DeviceRepository;
import com.vollify.smart.device.service.common.AbstractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttributeService extends AbstractService<Attribute> {

    private final AttributeRepository attributeRepository;

    public Optional<Attribute> findAttributeByName(String name){
        return attributeRepository.findAttributeByName(name);
    }

    public List<Attribute> findAttributesByTypes(Type type){
        return attributeRepository
                .findAll()
                .stream()
                .filter(attribute -> attribute.getTypes().contains(type))
                .collect(Collectors.toList());
    }

    @Override
    protected MongoRepository<Attribute, String> getDao() {
        return attributeRepository;
    }

}
