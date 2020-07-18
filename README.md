# CoolDiEye
Very cool and lightweight DI framework in Java 1.8+

## Design considerations
* For flexible, we are using xml to define the DI configuration, thanks for help of fasterxml framework, it help to simplify the parsing xml file. 
* Start with basic DI feture which is using everyday: nested bean, scope (singleton, prototype) and close (to destroy the container)
* Use Java 1.8 as still have alot of prjects are in java 1.8
* Use very popular testing framework Junit and Mockito for unit testing. 
* Use Java Reflector to create object and call method at runtime


## Limitations
* Only support field-based injection (no constructor-based injection)
* No anotation syntax support
* No cyclic dependency, lazy, primitive value.. supported yet!

## Getting Started / Usage Guide
### Dependencies
* FasterXML: https://github.com/FasterXML
### Installing
* Download and add jar file to your project
* No maven support yet
### Quickstart
* Let say your project already have MessageApp and MessageService classes like these
```java
public class SmsService implements MessageService {
    public String send(String message, String address) {
        System.out.println("SmsService : message '" + message + "' was sent to mobile number '" + address + "'");
        return "SmsService";
    }
}
public class EmailService implements MessageService {

    public String send(String message, String address) {
        System.out.println("EmailService : message '" + message + "' was sent to email address '" + address + "'");
        return "EmailService";
    }
}

public class MessageApp {
    private MessageService messageService;
    
    public String sendMessage(String message, String address) {
        return messageService.send(message, address);
    }
}
```
* You want DI framework help create instance and manage dependency of these objects

* Create 'applicationContext.xml' resource in your project
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans>
    <bean id="emailService" class="com.david.support.service.EmailService"/>
    <bean id="messageApp" class="com.david.support.MessageApp" scope="singleton">
        <property name="messageService" ref="emailService"/>
    </bean>
</beans>
```
* Create CoolDiEye's applicationContext and use it to get the bean with dependencies for you
```java
XmlApplicationContext context = new XmlApplicationContext("applicationContext.xml");
MessageApp app = context.getBean("messageApp");

String deliveryService = app.sendMessage("Hi DiEye", "a1@gmail.com");

Assert.assertEquals("EmailService", deliveryService);

// close the context
context.close();
```
## Usage
### Syntax in XML file
Name | Desc
------------ | -------------
bean | To define bean with mandatory "id" and "class" attribute
bean.scope | 'singleton' or 'prototype'. Default is 'prototype'
bean.property | To define the property/field of a bean
property.name | The object field name
property.ref | Refer to 'id' of other bean

### Scope
* Only singleton and prototype
* Singleton mean: the framework will always return same instance.
* Prototype: everytime create new instance

### Lifecyle
* With prototype: actually not managed by framework, just help you to create it and forget
& With singleton: container will manage them, and when calling close on context/container then the "onDestroy" method of each bean will be triggerred. 

## Authors
Luong Thanh Hoai - Hoai0906@gmail.com

## License

This project is licensed under the 'public' License. Totally free to use and distribute!

## Acknowledgments
* Spring IoC: https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/beans.html




