package com.david.test;


import com.david.support.MessageApp;
import com.david.support.service.EmailService;
import com.david.support.service.MessageService;
import org.cooldieye.context.XmlApplicationContext;
import org.cooldieye.exception.BeanDefinitionNotFoundException;
import org.junit.Assert;
import org.junit.Test;

public class DILoadingTest {
    @Test
    public void testLoadDI_notFoundBeanInXmlContextGiven_shouldFail() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_email.xml");
        try {
            context.getBean("NotFoundBean");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof BeanDefinitionNotFoundException);
        }
    }

    @Test
    public void testLoadDI_simpleBeanGiven_shouldSuccess() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_simple.xml");
        MessageService service = context.getBean("emailService");

        Assert.assertTrue(service instanceof EmailService);

        // close the context
        context.close();
    }

    @Test
    public void testLoadDI_complexBeanGiven_shouldSuccess() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_email.xml");
        MessageApp app = context.getBean("messageApp");

        Assert.assertTrue(app.getMessageService() instanceof EmailService);

        // close the context
        context.close();
    }

    @Test
    public void testLoadDI_emailSetupGiven_shouldDeliverByEmail() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_email.xml");
        MessageApp app = context.getBean("messageApp");

        String deliveryService = app.sendMessage("Hi DiEye", "a1@gmail.com");

        Assert.assertEquals("EmailService", deliveryService);

        // close the context
        context.close();
    }

    @Test
    public void testLoadDI_smsSetupGiven_shouldDeliverByEmail() {
        //init context
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_sms.xml");
        MessageApp app = context.getBean("messageApp");

        String deliveryService = app.sendMessage("Hi DiEye", "123");

        Assert.assertEquals("SmsService", deliveryService);

        // close the context
        context.close();
    }

    @Test
    public void testLoadDI_closeContext_shouldSuccess() {
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_sms.xml");
        context.getBean("messageApp");

        Assert.assertTrue(context.getManagedBeanMap().size() > 0);

        context.close();

        Assert.assertEquals(0, context.getManagedBeanMap().size());
    }


}
