package com.vollify.smart.controller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vollify.smart.controller.model.dto.AttributeDto;
import com.vollify.smart.controller.model.dto.DeviceDto;
import com.vollify.smart.controller.model.dto.RequestPayloadDto;
import com.vollify.smart.controller.service.common.RestTemplateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class RabbitMqTestSetRunnerConsumerService {

    @Autowired
    RestTemplateWrapper restTemplateWrapper;
    @Autowired
    private RabbitQueueServiceImpl rabbitQueueService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RabbitListener(id = "data-collector-exchange")
    private void receiver(String testSetId, @Header(AmqpHeaders.CONSUMER_QUEUE) String queue, @Header("device_id") String deviceId) throws JsonProcessingException {
        final AtomicBoolean direct = new AtomicBoolean(false);
        final AtomicReference<String> id = new AtomicReference<>("");

        log.info("Received Message from queue: " + queue);

        final DeviceDto deviceDto = restTemplateWrapper.getForEntity(DeviceDto.class, "http://device/device/" + deviceId);
        final List<AttributeDto> attributeDtos = restTemplateWrapper.getForList(AttributeDto.class, "http://device/device/attribute");

        attributeDtos.forEach(attributeDto -> {
            if (attributeDto.getName().equals("DIRECT-COLLECTOR-EXCHANGE")) {
                direct.set(true);
                id.set(attributeDto.getId());
            }
        });

        if (direct.get()) {
            final RequestPayloadDto requestPayloadDto = new RequestPayloadDto(testSetId);
            final String url = "http://device/device/payload/" + deviceDto.getId() + "/" + id;
            restTemplateWrapper.postForEntity(DeviceDto.class, url, requestPayloadDto);
        }

    }

}
