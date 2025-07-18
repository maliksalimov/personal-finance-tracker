package com.maliksalimov.financetrackerapi.entity.enums;

public enum AccountType {
    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    CREDIT_CARD("Credit Card"),
    INVESTMENT("Investment Account"),
    CASH("Cash"),
    OTHER("Other");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}