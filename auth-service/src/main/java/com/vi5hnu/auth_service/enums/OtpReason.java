package com.vi5hnu.auth_service.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OtpReason {
    PASSWORD_UPDATE("PASSWORD_UPDATE"),
    PASSWORD_FORGOT("PASSWORD_FORGOT");
    public final String status;
}
