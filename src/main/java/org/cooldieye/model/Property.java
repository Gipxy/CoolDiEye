package org.cooldieye.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Property {
    @JsonProperty("name")
    private String name;

    @JsonProperty("ref")
    private String ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
