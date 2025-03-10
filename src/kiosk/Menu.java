package kiosk;

import java.util.List;

public class Menu {
    private final String category;
    private List<MenuItem> menuList;

    //생성자
    public Menu(String category){
        this.category = category;
    }

    //getter
    public MenuItem getMenuItem(int index){
        return this.menuList.get(index);
    }
    public String getCategory(){
        return this.category;
    }
    public Integer getListSize(){
        return this.menuList.size();
    }
    //setter
    public void addMenu(MenuItem item){
        this.menuList.add(item);
    }
}
