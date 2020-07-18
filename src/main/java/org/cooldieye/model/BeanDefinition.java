package org.cooldieye.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

import static org.cooldieye.model.BeanDefinitionConstant.SCOPE_SINGLETON;

public class BeanDefinition {
    @JsonProperty("id")
    private String id;

    @JsonProperty("class")
    private String clazz;

    @JsonProperty("scope")
    private String scope;

    @JacksonXmlElementWrapper(useWrapping=false)
    @JacksonXmlProperty(localName = "property")
    private List<Property> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isNotSingleton() {
        return !isSingleton();
    }
}
