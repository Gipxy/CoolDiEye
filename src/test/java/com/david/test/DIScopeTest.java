package com.david.test;


import com.david.support.MessageApp;
import com.david.support.service.MessageService;
import org.cooldieye.context.XmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;

public class DIScopeTest {

    @Test
    public void testScope_simpleSingletonGiven_shouldReturnSameObject() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext.xml");
        MessageService service1 = context.getBean("emailService");
        MessageService service2 = context.getBean("emailService");

        Assert.assertEquals(service1, service2);
    }

    @Test
    public void testScope_simplePrototypeGiven_shouldReturnNewObject() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext.xml");
        MessageService service1 = context.getBean("smsService");
        MessageService service2 = context.getBean("smsService");

        Assert.assertNotEquals(service1, service2);
    }

    @Test
    public void testScope_singletonInChildGiven_shouldReturnSameObject() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext.xml");
        MessageApp app1 = context.getBean("messageApp_child_singleton");
        MessageApp app2 = context.getBean("messageApp_child_singleton");

        Assert.assertEquals(app1.getMessageService(), app2.getMessageService());
    }

    @Test
    public void testScope_prototypeInChildGiven_shouldReturnSameObject() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext.xml");
        MessageApp app1 = context.getBean("messageApp_child_prototype");
        MessageApp app2 = context.getBean("messageApp_child_prototype");

        Assert.assertNotEquals(app1.getMessageService(), app2.getMessageService());
    }
}
