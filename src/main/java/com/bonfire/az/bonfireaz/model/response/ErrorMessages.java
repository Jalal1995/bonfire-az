package com.bonfire.az.bonfireaz.model.response;

public enum ErrorMessages {
    NO_RECORD_FOUND("Record with provided id is not found"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    MISSING_REQUIRED_FIELD("Missing required field");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
