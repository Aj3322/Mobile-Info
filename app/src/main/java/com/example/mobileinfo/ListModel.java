package com.example.mobileinfo;

public class ListModel {
    String Name;
    String Info;

    String titel;
    private ThreeState state;
    int type =0;
    private String suffix = "";

    public ListModel(String name, String info, ThreeState state, int type, String suffix) {
        Name = name;
        Info = info;
        this.state = state;
        this.type = type;
        this.suffix = suffix;
    }


    public ListModel(String name, String info, String suffix) {
        Name = name;
        Info = info;
        this.suffix = suffix;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public ListModel() {
    }

    public ListModel(String name, String info) {
        this.Name = name;
        this.Info = info;
    }

    public ListModel(String label, ThreeState state) {
        this.Name = label;
        this.state = state;
    }

    public ListModel(String title, int Type) {
       this.titel= title;
       this.type= Type;

    }

    public ListModel(String name, ThreeState state, int suffix) {
        Name = name;
        this.state = state;
        this.type = suffix;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
    public ThreeState getState() {
        return state;
    }

    public void setState(ThreeState state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
