package com.vollify.smart.device.service;

import com.vollify.smart.device.model.Attribute;
import com.vollify.smart.device.model.Device;
import com.vollify.smart.device.model.State;
import com.vollify.smart.device.repository.DeviceRepository;
import com.vollify.smart.device.service.common.AbstractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        return super.create(fillEntityWithStates(entity));
    }

    @Override
    public Device update(Device entity) {
        entity.setTopic(setTopicFromDevice(entity));
        return super.update(fillEntityWithStates(entity));
    }

    private String setTopicFromDevice(Device entity) {
        return ((BASETOPIC + "/" + entity.getRoom() + "/" + entity.getType().getName() + "/" + entity.getName()).replace(" ", "").toLowerCase());
    }

    @Override
    protected MongoRepository<Device, String> getDao() {
        return deviceRepository;
    }


    private Device fillEntityWithStates(Device device){
            List<Attribute> attributes = attributeService.findAttributesByTypes(device.getType());

            log.info(attributes.toString());
            device.getStates().clear();
            attributes.forEach(attribute ->
                    device.getStates().add(new State(attribute, attribute.getDefaultValue()))
            );

            return device;
    }
}
