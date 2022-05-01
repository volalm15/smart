package com.vollify.smart.device.controller;

import com.vollify.smart.device.exception.EntityNotFoundException;
import com.vollify.smart.device.model.Attribute;
import com.vollify.smart.device.model.Type;
import com.vollify.smart.device.model.dto.AttributeDto;
import com.vollify.smart.device.service.AttributeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Validated
public class AttributeController implements AttributeEndpoint {

    @Autowired
    AttributeService attributeService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<AttributeDto> createAttribute(final AttributeDto attributeDto) throws EntityNotFoundException {
        log.info("Received createAttribute Request - Body: " + attributeDto.toString());

        final Attribute attribute = convertToEntity(attributeDto);
        final Attribute attributeCreated = attributeService.create(attribute);
        return ResponseEntity.ok(convertToDto(attributeCreated));
    }

    @Override
    public ResponseEntity<AttributeDto> getAttributeById(final String id) throws EntityNotFoundException {
        log.info("Received getAttribute Request - ID: " + id);

        Optional<Attribute> oldAttributeOptional = attributeService.findById(id);
        return oldAttributeOptional.map(attribute -> ResponseEntity.ok(convertToDto(attribute))).orElseThrow(() -> new EntityNotFoundException("Attribute not found"));

    }

    @Override
    public ResponseEntity<List<AttributeDto>> getAttributes(Optional<String> nameOptional, Optional<Type> typeOptional) {
        log.info("Received getAttributes Request");

        return ResponseEntity.ok(attributeService.findAll().stream().map(this::convertToDto).filter(attributeDto -> nameOptional.isEmpty() || attributeDto.getName().equals(nameOptional.get())).filter(attributeDto -> typeOptional.isEmpty() || attributeDto.getTypes().contains(typeOptional.get())).collect(Collectors.toList()));


    }

    @Override
    public ResponseEntity<AttributeDto> updateAttribute(final String id, final AttributeDto attributeDto) throws EntityNotFoundException {
        if (!id.equals(attributeDto.getId())) throw new ValidationException("Given IDs don't match");

        log.info("Received updateAttribute Request -ID:" + id + " -Body: " + attributeDto);

        final Attribute attribute = convertToEntity(attributeDto);
        return ResponseEntity.ok(convertToDto(attributeService.update(attribute)));
    }

    @Override
    public ResponseEntity<AttributeDto> deleteAttribute(String id) throws EntityNotFoundException {
        log.info("Received deleteDevice Request - ID: " + id);

        attributeService.findById(id).orElseThrow(() -> new EntityNotFoundException("Attribute not found"));
        attributeService.delete(id);
        return ResponseEntity.ok().build();
    }

    private AttributeDto convertToDto(Attribute attribute) {
        log.info("Try to map entity to DTO: " + attribute);

        final AttributeDto attributeDto = modelMapper.map(attribute, AttributeDto.class);
        log.info("Successfully mapped entity to DTO: " + attributeDto);

        return attributeDto;

    }

    private Attribute convertToEntity(AttributeDto attributeDto) throws EntityNotFoundException {
        log.info("Try to map DTO to entity: " + attributeDto);

        Attribute attribute = modelMapper.map(attributeDto, Attribute.class);
        if (attributeDto.getId() != null) {
            Optional<Attribute> oldAttributeOptional = attributeService.findById(attributeDto.getId());
            oldAttributeOptional.ifPresent(oldAttribute -> attribute.setId(oldAttribute.getId()));
            oldAttributeOptional.orElseThrow(() -> new EntityNotFoundException("Attribute not found"));
        }
        log.info("Successfully mapped DTO to entity: " + attribute);

        return attribute;
    }

}
