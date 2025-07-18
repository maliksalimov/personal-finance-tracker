package com.maliksalimov.financetrackerapi.entity.enums;

import lombok.Getter;

@Getter
public enum Currency {
    AZN("AZN", "Azerbaijani Manat"),
    USD("USD", "American Dallir"),
    EUR("EUR", "Euro"),
    RUB("RUB", "Russian Ruble");

    private final String code;
    private final String displayName;

    Currency(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
