package com.demo.Gradle.demo.enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String value;

    Status(String value) {
        this.value = value;
    }

}
