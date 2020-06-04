package com.rakib.mosjid;

public class DataStore {
    private String name,fName,phone,status,id;

    public DataStore() {
    }

    public DataStore(String name, String fName) {
        this.name = name;
        this.fName = fName;
    }

    public DataStore(String name, String fName, String id) {
        this.name = name;
        this.fName = fName;
        this.id = id;
    }

    public DataStore(String name, String fName, String phone, String status, String id) {
        this.name = name;
        this.fName = fName;
        this.phone = phone;
        this.status = status;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
