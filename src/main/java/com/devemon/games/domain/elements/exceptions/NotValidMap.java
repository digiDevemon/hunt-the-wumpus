package com.devemon.games.domain.elements.exceptions;

public class NotValidMap extends RuntimeException {
    public NotValidMap() {
        super("Trying to build a not completely map");
    }
}