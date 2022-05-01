package com.vollify.smart.controller.service.common;

public interface RabbitQueueService {
    void addNewQueue(String queueName, String exchangeName, String routingKey, boolean registerListener);

    void addQueueToListener(String listenerId, String queueName);

    void removeQueueFromListener(String listenerId, String queueName);

    Boolean checkQueueExistOnListener(String listenerId, String queueName);
}