package pl.rogalik.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {

    private String name;
    private String type;
    private List<Attribute> attributes;

    public Item(String name){
        this.name = name;
        this.type = type;
        this.attributes = new ArrayList<Attribute>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

}
