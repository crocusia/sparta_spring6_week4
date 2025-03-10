package kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Kiosk {
    //MenuItem을 관리하는 리스트
    private List<MenuItem> menuItems;

    //Kiosk 객체 생성 시, 값을 넘겨줌
    public Kiosk(){
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("ShackBurger", 6.9, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        menuItems.add(new MenuItem("SmokeShack", 8.9, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        menuItems.add(new MenuItem("Cheeseburger", 6.9, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        menuItems.add(new MenuItem("Hamburger", 5.4, "비프패티를 기반으로 야채가 들어간 기본버거"));
    }

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
        try {
            do {
                //입력 받기 & 개행 문자 제거
                choice = input.nextInt();
                input.nextLine();
            } while (choice != 0);
        } catch(InputMismatchException e){
            System.out.println("잘못된 형식의 입력입니다.");
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("해당 값은 선택할 수 없습니다.");
        } catch(Exception e){
            System.out.println("알 수 없는 오류입니다." + e);
        }
        System.out.println("프로그램을 종료합니다.");
    }
}
