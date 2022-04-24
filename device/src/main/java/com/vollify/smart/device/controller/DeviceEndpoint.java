package com.vollify.smart.device.controller;

import com.vollify.smart.device.exception.EntityNotFoundException;
import com.vollify.smart.device.model.dto.DeviceDto;
import com.vollify.smart.device.model.dto.RequestDeviceDto;
import com.vollify.smart.device.model.dto.RequestPayloadDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/device", produces = "application/json")
public interface DeviceEndpoint {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DeviceDto> createDevice(@Valid @RequestBody RequestDeviceDto requestDeviceDto) throws EntityNotFoundException;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<DeviceDto>> getDevices();

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<DeviceDto> getDevice(@Valid @PathVariable String id) throws EntityNotFoundException;

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<DeviceDto> updateDevice(@Valid @PathVariable String id, @Valid @RequestBody RequestDeviceDto requestDeviceDto) throws EntityNotFoundException;

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<DeviceDto> deleteDevice(@Valid @PathVariable String id) throws EntityNotFoundException;

    @PostMapping(path = "/payload/{deviceId}/{attributeId}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<DeviceDto> addPayload(@Valid @PathVariable String deviceId, @Valid @PathVariable String attributeId, @Valid @RequestBody RequestPayloadDto payload) throws EntityNotFoundException;
}
