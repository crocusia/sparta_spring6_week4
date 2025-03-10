package kiosk;

import java.util.List;
import java.util.Scanner;

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

    //main에서 관리하던 입력과 반복문 로직 관리
    public void start(){
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
