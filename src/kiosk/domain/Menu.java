package kiosk.domain;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final String category;
    private List<MenuItem> menuList;

    //생성자
    public Menu(String category){
        this.category = category;
        menuList = new ArrayList<>();
    }

    //getter
    public String getCategory(){
        return this.category;
    }
    public Integer getListSize(){
        return this.menuList.size();
    }
    public MenuItem getMenuItem(int index){
        return this.menuList.get(index);
    }

    //setter
    public void addMenu(MenuItem item){
        this.menuList.add(item);
    }

    //카테고리 내 메뉴 출력 메서드
    public void printMenuList() {
        System.out.println("[ " + category + " MENU" + " ]");
        for (int i = 1; i <= menuList.size(); i++) {
            System.out.println(i + ". " + menuList.get(i - 1));
        }
    }
}
