package com.example.backendapitaskmanager.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "tasks")
    public void receive(String message) {
        logger.info(message);
    }
}
