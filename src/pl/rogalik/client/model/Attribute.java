package pl.rogalik.client.model;

import java.io.Serializable;

public class Attribute implements Serializable {

    String name;

    Object value;

    public Attribute(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
