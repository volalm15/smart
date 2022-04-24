package com.vollify.smart.device.service;

import com.vollify.smart.device.model.Attribute;
import com.vollify.smart.device.model.Device;
import com.vollify.smart.device.model.Payload;
import com.vollify.smart.device.model.Property;
import com.vollify.smart.device.repository.DeviceRepository;
import com.vollify.smart.device.service.common.AbstractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceService extends AbstractService<Device> {
    public static String BASETOPIC = "smart";

    private final DeviceRepository deviceRepository;

    @Autowired
    private final AttributeService attributeService;

    @Override
    public Device create(Device entity) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setLastModification(LocalDateTime.now());
        entity.setTopic(setTopicFromDevice(entity));

        return super.create(fillEntityWithTransaction(entity, true));
    }

    @Override
    public Device update(Device entity) {
        entity.setTopic(setTopicFromDevice(entity));
        return super.update(fillEntityWithTransaction(entity, false));
    }

    private String setTopicFromDevice(Device entity) {
        return ((BASETOPIC + "/" + entity.getArea() + "/" + entity.getType().getName() + "/" + entity.getName()).replace(" ", "").toLowerCase());
    }

    @Override
    protected MongoRepository<Device, String> getDao() {
        return deviceRepository;
    }


    private Device fillEntityWithTransaction(Device device, boolean ignorePayload) {
        final List<Attribute> attributes = attributeService.findAttributesByTypes(device.getType()); //all available attributes
        final List<Property> oldProperties = List.copyOf(device.getProperties()); //properties of old device
        device.getProperties().clear();

        attributes.forEach(attribute -> device.getProperties().add(new Property(attribute, Collections.emptyList())));

        //fill payload if exists
        if (!ignorePayload) {
            device.getProperties().forEach(property -> oldProperties.forEach(oldProperty -> {
                        if (oldProperty.getAttribute() != null) {
                            if (property.getAttribute().getId().equals(oldProperty.getAttribute().getId())) {
                                property.setPayload(oldProperty.getPayload());
                            }
                        }

                    })
            );
        }
        return device;
    }

    public Device addPayload(Device device, String attributeId, Payload payload) {
        device.getProperties().forEach(property -> {
            if (attributeId.equals(property.getAttribute().getId())) {
                property.getPayload().add(payload);
            }
        });
        update(device);
        return device;
    }
}
