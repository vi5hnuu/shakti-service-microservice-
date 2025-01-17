package com.vi5hnu.app_service.enums;

public enum AccountType {
    GOOGLE("GOOGLE"),
    MANUAL("MANUAL");
    public final String type;

    // Constructor
    AccountType(String type) {
        this.type = type;
    }
}
