package kiosk;

import javax.lang.model.type.NullType;

public class MenuItem {
    //캡슐화
    private String name;
    private Double price;
    private String description;
    //생성자
    public MenuItem(String name, Double price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }
    //getter
    public String getName(){
        return this.name;
    }
    public Double getPrice(){
        return this.price;
    }
    public String getDescription(){
        return this.description;
    }
    //setter
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(Double price){
        this.price = price;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
