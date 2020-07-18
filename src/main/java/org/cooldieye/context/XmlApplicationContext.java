package org.cooldieye.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.cooldieye.model.BeanDefinition;
import org.cooldieye.model.BeanDefinitionList;
import org.cooldieye.model.Property;
import org.cooldieye.exception.BeanDefinitionNotFoundException;
import org.cooldieye.exception.ContextResourceLoadingException;
import org.cooldieye.helper.ReflectorHelper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class XmlApplicationContext implements ApplicationContext {
    public static final String ON_DESTROY_METHOD_NAME = "onDestroy";
    private String xmlContextResource;

    //
    private Map<String, Object> managedBeanMap = new HashMap<String, Object>();

    //To store definition of the bean, data fill be filled when loadXML configuration file.
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    public XmlApplicationContext(String xmlContextResource) {
        this.xmlContextResource = xmlContextResource;
        loadXml();
    }

    private void loadXml() {
        ObjectMapper xmlMapper = new XmlMapper();
        try {
            InputStream ctxAsStream = this.getClass().getClassLoader().getResourceAsStream(xmlContextResource);
            BeanDefinitionList beans = xmlMapper.readValue(ctxAsStream, BeanDefinitionList.class);

            for (BeanDefinition bean : beans.getBeanDefinitions()) {
                beanDefinitionMap.put(bean.getId(), bean);
            }

        } catch (Exception e) {
            throw new ContextResourceLoadingException(e);
        }
    }

    public void close() {
        //destroy each bean
        for (Entry entry : managedBeanMap.entrySet()) {
            Object object = entry.getValue();
            ReflectorHelper.invokeMethodIfExist(object, ON_DESTROY_METHOD_NAME);
        }

        //destroy container
        managedBeanMap.clear();
    }

    public <T> T getBean(String beanId) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanId);

        if (beanDefinition == null) {
            throw new BeanDefinitionNotFoundException("Bean not found!");
        }

        return (T) getOrCreateBean(beanDefinition);
    }

    private Object getOrCreateBean(BeanDefinition beanDefinition) {
        Object bean = null;
        if (beanDefinition.isSingleton()) {
            //try to get from managed beans
            bean = managedBeanMap.get(beanDefinition.getId());
        }
        //Can't get from managedBean or not singleton bean
        if (bean == null) {
            bean = createSingleBean(beanDefinition);
        }
        //recursive for all children
        List<Property> properties = beanDefinition.getProperties();
        if (properties!=null) {
            for (Property property : properties) {
                String refBeanId = property.getRef();
                Object childBean = getBean(refBeanId);

                String fieldName = property.getName();
                ReflectorHelper.setField(bean, fieldName, childBean);
            }
        }

        //put back to managed list if is singleton
        if (beanDefinition.isSingleton()) {
            managedBeanMap.put(beanDefinition.getId(), bean);
        }

        return bean;
    }

    private Object createSingleBean(BeanDefinition beanDefinition) {
        return ReflectorHelper.newDefaultInstance(beanDefinition.getClazz());
    }

    public Map<String, Object> getManagedBeanMap() {
        return managedBeanMap;
    }


}
