package com.david.test;


import com.david.support.MessageApp;
import org.cooldieye.context.XmlApplicationContext;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DICloseTest {
    @Test
    public void testLoadDI_closeContext_shouldManagedBeanEmpty() {
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_sms.xml");
        context.getBean("messageApp");

        Assert.assertTrue(context.getManagedBeanMap().size() > 0);

        context.close();

        Assert.assertEquals(0, context.getManagedBeanMap().size());
    }

    @Test
    public void testLoadDI_closeContext_shouldCallOnDestroy() {
        XmlApplicationContext context = new XmlApplicationContext("messageApplicationContext_close.xml");
        String appBeanId = "messageApp";

        MessageApp app = context.getBean(appBeanId);
        MessageApp appSpied = spy(app);

        context.getManagedBeanMap().put(appBeanId, appSpied);

        verify(appSpied, times(0)).onDestroy();

        context.close();

        verify(appSpied, times(1)).onDestroy();
    }

}
