package com.cg.model;

public class Category {

    protected int id;
    protected String name;


    public Category() {
    }


    public Category(int id, String name) {
        super();
        this.id = id;
        this.name = name;

    }


    public Category(String name) {
        super();
        this.name = name;

    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


}
