package kiosk;

import jdk.jshell.SourceCodeAnalysis;

import java.util.Scanner;

public class Main {
    public static void printTitle(String title){
        System.out.println("[ " + title + " ]");
    }
    public static void printMenuItem(int index, MenuItem item){
        String name = item.getName();
        Double price = item.getPrice();
        String desc = item.getDescription();
        System.out.printf("%d. %-15s| W %.1f | %s%n", index, name, price, desc);
    }
    public static void printExit(){
        System.out.println("0. 종료");
    }

    public static void main(String[] args){
        Menu burgerMenu = new Menu("Burger");
        burgerMenu.addMenu(new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));

        Scanner input = new Scanner(System.in);
        int choice = 0;
        do{
            printTitle("SHAKESHACK MENU");
            for(int i = 1; i < burgerMenu.getListSize()+1; i++){
                printMenuItem(i, burgerMenu.getMenuItem(i-1));
            }
            printExit();

            choice = input.nextInt();
            input.nextLine();
        }while(choice != 0);
        System.out.println("프로그램을 종료합니다.");
    }
}
