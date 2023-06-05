package com.cokroktoupadek.atiperatask.errorhandlers;

public enum ErrorMessagesEnum {
    USER_NOT_FOUND("User not found. Check for typos in username."),
    NOT_ACCEPTABLE("Header argument not valid."),
    INTERNAL_SERVER_ERROR("Internal application exception. Verify network connection, GitHub Api status or headers");

    public final String value;
    ErrorMessagesEnum(String value) {
        this.value = value;
    }
}
