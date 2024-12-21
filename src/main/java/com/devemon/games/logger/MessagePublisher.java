package com.devemon.games.logger;

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


    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private static Logger logger = LoggerFactory.getLogger(MessagePublisher.class);
}
