package com.vollify.smart.controller.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqTestSetRunnerConsumerService {

    @Autowired
    RabbitQueueServiceImpl rabbitQueueService;

    @RabbitListener(id = "test-set-runner-exchange", queues = {}, concurrency = "")
    public void receiver(Long testSetId) {
        log.info("received Message from rabbit : " + testSetId);
        try {
            log.info("completed " + testSetId + " task");
        } catch (Exception e) {
            log.error("Error on running test set");
            log.error("Error message : " + ExceptionUtils.getMessage(e));
            log.error("Error trace : " + ExceptionUtils.getStackTrace(e));
        }
    }
}
