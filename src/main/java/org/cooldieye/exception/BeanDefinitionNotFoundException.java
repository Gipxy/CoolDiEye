package org.cooldieye.exception;

public class BeanDefinitionNotFoundException extends RuntimeException {
    public BeanDefinitionNotFoundException(String message) {
        super(message);
    }
}
