package com.bonfire.az.bonfireaz.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 7731160215367344274L;

    public UserServiceException(String message) {
        super(message);
        log.warn("Exception: {}", message);
    }
}
