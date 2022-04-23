package com.vollify.smart.device.controller;

import com.vollify.smart.device.exception.EntityNotFoundException;
import com.vollify.smart.device.model.Type;
import com.vollify.smart.device.model.dto.AttributeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/device/attribute", produces = "application/json")
public interface AttributeEndpoint {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<AttributeDto> createAttribute(@Valid @RequestBody AttributeDto attributeDto) throws EntityNotFoundException;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<AttributeDto>> getAttributes(@RequestParam(value = "name", required = false) Optional<String> name, @RequestParam(value = "type", required = false) Optional<Type> type);

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<AttributeDto> getAttributeById(@Valid @PathVariable String id) throws EntityNotFoundException;

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<AttributeDto> updateAttribute(@Valid @PathVariable String id, @Valid @RequestBody AttributeDto attributeDto) throws EntityNotFoundException;

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<AttributeDto> deleteAttribute(@Valid @PathVariable String id) throws EntityNotFoundException;
}
