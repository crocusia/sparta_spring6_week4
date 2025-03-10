package kiosk;

import jdk.jshell.SourceCodeAnalysis;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //Menu 객체 생성 및 MenuItem 객체 생성 후 리스트에 add
        Menu burgerMenu = new Menu("Burger");
        burgerMenu.addMenu(new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        burgerMenu.addMenu(new MenuItem("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));

        //Scanner로 숫자를 입력받기
        Scanner input = new Scanner(System.in);
        int choice = 0;
        //반복문을 이용해 0 입력 시, 프로그램 종료
        do{
            //입력 받기
            choice = input.nextInt();
            //개행 문자 제거
            input.nextLine();
        }while(choice != 0);
        System.out.println("프로그램을 종료합니다.");
    }
}
