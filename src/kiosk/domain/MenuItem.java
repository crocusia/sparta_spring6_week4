package kiosk.domain;

public class MenuItem {
    //캡슐화 - 이름, 가격, 설명 필드
    private String name;
    private double price;
    private String description;

    //생성자
    public MenuItem(String name, double price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }

    //getter
    public String getName(){
        return this.name;
    }
    public double getPrice(){
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

    //출력
    @Override
    public String toString(){
        return String.format("%-15s| W %.1f | %s", this.name, this.price, this.description);
    }
}
