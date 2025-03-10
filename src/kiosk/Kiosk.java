package kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Kiosk {
    //MenuItem을 관리하는 리스트
    private List<Menu> category;

    //Kiosk 객체 생성 시, 값을 넘겨줌
    public Kiosk(){
        category = new ArrayList<>();
    }

    //getter
    public Menu getCategory(int index){
        return category.get(index);
    }
    public int getCategorySize(){
        return category.size();
    }

    //setter
    public void addCategory(Menu menu){
        this.category.add(menu);
    }

    public void printMenuItem(MenuItem item){
        String name = item.getName();
        Double price = item.getPrice();
        String desc = item.getDescription();
        System.out.printf("%-15s| W %.1f | %s%n", name, price, desc);
    }

    public void printList(String title){
        System.out.println("[ " + title + " ]");
        for(int i = 1; i < menuItems.size()+1; i++){
            System.out.printf("%d. ", i);
            printMenuItem(menuItems.get(i-1));
        }
        System.out.println("0. 종료");
    }

    //main에서 관리하던 입력과 반복문 로직 관리
    public void start(){
        Scanner input = new Scanner(System.in);
        int choice = -1;
        //반복문을 이용해 0 입력 시, 프로그램 종료
        do {
            try {
                //출력
                printList("SHAKESHACK MENU");
                //입력 받기 & 개행 문자 제거
                choice = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 형식의 입력입니다.");
                input.nextLine();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("해당 값은 선택할 수 없습니다.");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("알 수 없는 오류입니다." + e);
            }
        } while (choice != 0);
        System.out.println("프로그램을 종료합니다.");
    }
}
