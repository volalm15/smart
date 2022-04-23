package com.vollify.smart.device.repository;


import com.vollify.smart.device.model.Attribute;
import com.vollify.smart.device.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device, String> {
}
