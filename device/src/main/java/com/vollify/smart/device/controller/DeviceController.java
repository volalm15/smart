package com.vollify.smart.device.controller;

import com.vollify.smart.device.exception.EntityNotFoundException;
import com.vollify.smart.device.model.Device;
import com.vollify.smart.device.model.Payload;
import com.vollify.smart.device.model.dto.DeviceDto;
import com.vollify.smart.device.model.dto.RequestDeviceDto;
import com.vollify.smart.device.model.dto.RequestPayloadDto;
import com.vollify.smart.device.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Validated
public class DeviceController implements DeviceEndpoint {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<DeviceDto> createDevice(final RequestDeviceDto requestDeviceDto) throws EntityNotFoundException {
        log.info("Received createDevice Request - Body: " + requestDeviceDto.toString());

        final Device device = convertToEntity(requestDeviceDto);
        final Device deviceCreated = deviceService.create(device);
        return ResponseEntity.ok(convertToDto(deviceCreated));
    }

    @Override
    public ResponseEntity<DeviceDto> getDevice(final String id) throws EntityNotFoundException {
        log.info("Received getDevice Request - ID: " + id);

        Optional<Device> deviceOptional = deviceService.findById(id);
        return deviceOptional
                .map(device -> ResponseEntity.ok(convertToDto(device)))
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

    }

    @Override
    public ResponseEntity<List<DeviceDto>> getDevices() {
        log.info("Received getDevices Request");

        return ResponseEntity.ok(deviceService.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<DeviceDto> addPayload(final String deviceId, final String attributeId, final RequestPayloadDto requestPayload) throws EntityNotFoundException {
        log.info("Received addPayload Request - ID: " + deviceId);

        Optional<Device> deviceOptional = deviceService.findById(deviceId);
        final Payload payload = new Payload(LocalDateTime.now(), requestPayload.getContent());
        return deviceOptional
                .map(device -> ResponseEntity.ok(convertToDto(deviceService.addPayload(device, attributeId, payload))))
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

    }


    @Override
    public ResponseEntity<DeviceDto> updateDevice(final String id, final RequestDeviceDto requestDeviceDto) throws EntityNotFoundException {
        if (!id.equals(requestDeviceDto.getId()))
            throw new ValidationException("Given IDs don't match");

        log.info("Received updateDevice Request -ID:" + id + " -Body: " + requestDeviceDto);

        final Device device = convertToEntity(requestDeviceDto);
        return ResponseEntity.ok(convertToDto(deviceService.update(device)));
    }

    @Override
    public ResponseEntity<DeviceDto> deleteDevice(String id) throws EntityNotFoundException {
        log.info("Received deleteDevice Request - ID: " + id);

        deviceService.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        deviceService.delete(id);
        return ResponseEntity.ok().build();
    }

    private DeviceDto convertToDto(Device device) {
        log.info("Try to map entity to DTO: " + device);

        final DeviceDto deviceDto = modelMapper.map(device, DeviceDto.class);
        log.info("Successfully mapped entity to DTO: " + deviceDto);

        return deviceDto;

    }

    private Device convertToEntity(RequestDeviceDto requestDeviceDto) throws EntityNotFoundException {
        log.info("Try to map DTO to entity: " + requestDeviceDto);

        Device device = modelMapper.map(requestDeviceDto, Device.class);
        if (requestDeviceDto.getId() != null) {
            Optional<Device> oldDeviceOptional = deviceService.findById(requestDeviceDto.getId());
            oldDeviceOptional.ifPresent(oldDevice -> {
                device.setId(oldDevice.getId());
                device.setLastModification(LocalDateTime.now());
                device.setCreatedAt(oldDevice.getCreatedAt());
                device.setProperties(oldDevice.getProperties());
            });
            oldDeviceOptional.orElseThrow(() -> new EntityNotFoundException("Device not found"));
        }
        log.info("Successfully mapped DTO to entity: " + device);

        return device;
    }

}
