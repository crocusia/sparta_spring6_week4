package kiosk;

import java.util.List;

public class Kiosk {

    public void printMenuItem(MenuItem item){
        String name = item.getName();
        Double price = item.getPrice();
        String desc = item.getDescription();
        System.out.printf("%-15s| W %.1f | %s%n", name, price, desc);
    }

    public void printList(String title, Menu menu){
        System.out.println("[ " + title + " ]");
        for(int i = 1; i < menu.getListSize()+1; i++){
            System.out.printf("%d. ", i);
            printMenuItem(menu.getMenuItem(i-1));
        }
        System.out.println("0. 종료");
    }
}
