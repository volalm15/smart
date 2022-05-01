package com.vollify.smart.controller.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vollify.smart.controller.model.dto.DeviceDto;
import com.vollify.smart.controller.service.RabbitQueueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TopicConstructor {

    public static String TOPIC_DELIMITER = ".";
    public static String TOPIC_EXPRESSION = "#";


    @Autowired
    RabbitQueueServiceImpl rabbitQueueService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private RestTemplateWrapper restTemplateWrapper;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() throws JsonProcessingException {
        List<DeviceDto> devices = restTemplateWrapper.getForList(DeviceDto.class, "http://device/device");
        updateTopics(devices);
    }

    private void updateTopics(List<DeviceDto> devices) {

        devices.forEach(deviceDto -> {
            final String[] topicTokens = deviceDto.getTopic().split("\\" + TOPIC_DELIMITER);
            final List<String> topics = new ArrayList<>();
            final AtomicBoolean directExchange = new AtomicBoolean(false);

            final String areaTopic = topicTokens[0] + TOPIC_DELIMITER + topicTokens[1] + TOPIC_DELIMITER;
            final String deviceTopic = areaTopic + topicTokens[2] + TOPIC_DELIMITER;

            deviceDto.getProperties().forEach(propertyDto -> {
                if (propertyDto.getAttribute().getName().equals("DIRECT-COLLECTOR-EXCHANGE")) {
                    directExchange.set(true);
                }
            });

            topics.add(deviceDto.getTopic());

            if (!directExchange.get()) {
                topics.add(areaTopic + TOPIC_EXPRESSION);
                topics.add(deviceTopic + TOPIC_EXPRESSION);
                topics.add(topicTokens[0] + TOPIC_DELIMITER + TOPIC_EXPRESSION);

                topics.forEach(s -> rabbitQueueService.addNewQueue(deviceDto.getTopic(), "data-publisher-exchange", s, false));
            } else {
                topics.forEach(s -> rabbitQueueService.addNewQueue(deviceDto.getTopic(), "data-collector-exchange", s, true));
            }
        });
    }
}
