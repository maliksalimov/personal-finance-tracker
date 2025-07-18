package com.maliksalimov.financetrackerapi.entity.enums;

import lombok.Getter;

@Getter
public enum RecurrenceInterval {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    QUARTERLY("Quarterly"),
    YEARLY("Yearly");

    private final String displayName;

    RecurrenceInterval(String displayName) {
        this.displayName = displayName;
    }

}