package com.vollify.smart.controller.model;

public enum Type {
    RELAIS("relais"), LIGHT("light"), GATE("gate"), SENSOR("sensor");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}