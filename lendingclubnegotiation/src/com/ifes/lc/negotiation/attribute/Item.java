package com.ifes.lc.negotiation.attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diegosoaresub on 28/05/17.
 */
public class Item {

    private String name;

    List<Attribute> attrs = new ArrayList<>();

    public Item(String name) {
        this.name = name;
    }

    public void addAttribute(String name, String nameOnModel, double weight, double pap, double min, double max, AttributeType type){
        this.attrs.add(new Attribute(name, nameOnModel, weight, pap, min, max, type));
    }

    public void addAttribute(Attribute attr){
        this.attrs.add(attr);
    }


    public Attribute getAttribute(String name){
        return this.attrs.stream().filter(a -> a.getName().equals(name)).findFirst().get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttrs() {
        return attrs;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", attrs=" + attrs +
                '}';
    }
}
