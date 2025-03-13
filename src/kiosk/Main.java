package kiosk;

import jdk.jshell.SourceCodeAnalysis;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //Menu 객체 생성 및 MenuItem 객체 생성 후 리스트에 add
        Menu burgerMenu = new Menu("Burgers");
        burgerMenu.addMenu(new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));

        Menu drinkMenu = new Menu("Drinks");
        drinkMenu.addMenu(new MenuItem("pepsi zero", 3.0, "펩시 제로 슈거 라임 맛"));

        Menu dessertMenu = new Menu("Desserts");
        dessertMenu.addMenu(new MenuItem("Anion Ring", 2.5, "양파를 통째로 튀긴 어니언링"));

        //Kiosk 객체 생성
        Kiosk kiosk = new Kiosk();
        //Menu를 Kiosk의 Menu 리스트에 저장
        kiosk.addCategory(burgerMenu);
        kiosk.addCategory(drinkMenu);
        kiosk.addCategory(dessertMenu);
        //키오스크 프로그램 시작
        kiosk.start();

    }
}
