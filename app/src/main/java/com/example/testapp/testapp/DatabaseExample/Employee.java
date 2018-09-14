package com.example.testapp.testapp.DatabaseExample;

/**
 * Created by admin0 on 28-Apr-17.
 */

class Employee {
    String id;
    String name;

    public Employee() {

    }

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Id " + id + " Name " + name ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
