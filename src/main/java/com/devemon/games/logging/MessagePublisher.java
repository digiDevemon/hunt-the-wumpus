package com.devemon.games.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
public class MessagePublisher implements Consumer<String> {


    @Override
    public void accept(String message) {
        logger.info(message);
    }

    public MessagePublisher() {
        this.logger = LoggerFactory.getLogger(MessagePublisher.class);
    }

    public MessagePublisher(Logger logger) {
        this.logger = logger;
    }

    private static Logger logger;
}
