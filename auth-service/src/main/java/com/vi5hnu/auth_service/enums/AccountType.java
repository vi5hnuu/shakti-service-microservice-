package com.vi5hnu.auth_service.enums;

import lombok.RequiredArgsConstructor;

public enum AccountType {
    GOOGLE("GOOGLE"),
    MANUAL("MANUAL");
    public final String type;

    // Constructor
    AccountType(String type) {
        this.type = type;
    }
}
