package com.vi5hnu.auth_service.enums;

import lombok.Getter;

@Getter
public enum Environment {
    development("dev"),
    production("prod");
    final String value;
    private Environment(String value){this.value=value;}
}
