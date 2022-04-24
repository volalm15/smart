package com.vollify.smart.controller.service;

import com.vollify.smart.controller.model.dto.DeviceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Constructor implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    RabbitQueueServiceImpl rabbitQueueService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ResponseEntity<List<DeviceDto>> response = restTemplate.exchange(
                "http://localhost:8085/device",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DeviceDto>>() {
                });
        List<DeviceDto> devices = response.getBody();
        devices.forEach(deviceDto -> rabbitQueueService
                .addNewQueue(deviceDto.getTopic(), "test-set-runner-exchange", deviceDto.getTopic()));
    }
}
