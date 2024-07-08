package com.example.backendapitaskmanager.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ listener for queues.
 */
@Component
public class RabbitMqListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    /**
     * First queue in app, logs just created task.
     * @param message Created task
     */
    @RabbitListener(queues = "tasks")
    public void receive(String message) {
        logger.info(message);
    }
}
