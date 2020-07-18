package org.cooldieye.context;

public interface ApplicationContext {
    <T> T getBean(String beanId);
    void close();
}
