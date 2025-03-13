package kiosk;

import java.awt.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

class ContinueException extends RuntimeException{} //메서드 내에서 continue와 같은 효과를 내기 위한 예외 생성

public class Kiosk {

    private List<Menu> category;    //MenuItem을 관리하는 리스트
    private Cart cart;              //장바구니 기능

    //현재 키오스크의 상태를 나타내는 변수
    private boolean isProcessing;
    private boolean isOrdering;     //메뉴를 선택 중인가? 주문하려는 상태인가?
    private int step;           //진행 단계
    private int categorySave;  //선택된 카테고리
    private int menuItemSave;  //선택된 메뉴

    //Kiosk 객체 생성 시, 값을 넘겨줌
    public Kiosk() {
        category = new ArrayList<>();
        cart = new Cart();
        isProcessing = true;
        isOrdering = false;
        step = 1;
        categorySave = 0;
        menuItemSave = 0;
    }

    //setter
    //카테고리에 MenuItem을 추가
    public void addCategory(Menu menu) {
        this.category.add(menu);
    }

    public void checkValidInput(int input){
        int minInput;
        int maxInput;
        switch(step){ //각 단계에 따른 입력 범위 설정
            case 1:
                minInput = 0;
                maxInput = category.size();
                if(cart.getCartSize() > 0){
                    maxInput = category.size()+2;
                }
                break;
            case 2:
                if(isOrdering){
                    minInput = 1;
                    maxInput = 2;
                }else{
                    minInput = 0;
                    maxInput = category.get(categorySave).getListSize();
                }
                break;
            case 3:
                minInput = 1;
                maxInput = 2;
                if(isOrdering){
                    maxInput = DiscountType.values().length;
                }
                break;
            default:
                throw new ContinueException();
        }

        if (input >= minInput && input <= maxInput) {
            if(input == 0){
                step--;
                if(step == 0){
                    isProcessing = false;
                }
            }else{
                step++;
            }
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }
    //step1 : 카테고리 선택지(+Order Menu 선택지)
    public void step1() {
        System.out.println("[ " + "MAIN MENU" + " ]");
        for (int i = 1; i < category.size() + 1; i++) {
            System.out.printf("%d. %s%n", i, category.get(i - 1).getCategory());
        }
        System.out.println("0. 종료");
        //장바구니가 비어있지 않다면, 결제를 위한 선택지 출력
        if (cart.getCartSize() > 0) {
            System.out.printf("%n[ " + "ORDER MENU" + " ]%n");
            System.out.printf("%d. %-15s| %s%n", category.size() + 1, "Orders", "장바구니를 확인 후 주문합니다.");
            System.out.printf("%d. %-15s| %s%n", category.size() + 2, "Cancel", "진행중인 주문을 취소합니다.");
        }
    }

    //step 2: 카테고리 내 메뉴 선택지 or 장바구니 정보 출력 후 선택지
    public void step2(int choice) {
        if (cart.getCartSize() > 0 && choice > category.size()) { //장바구니에 메뉴가 들어있다면 주문 가능한 상태
            if (choice == category.size()+1) {
                isOrdering = true;
                categorySave = choice-1;
                System.out.println("아래와 같이 주문하시겠습니까?");
                System.out.println("[ " + "Orders" + " ]");
                for (int i = 0; i < cart.getCartSize(); i++) {
                    cart.getCartItem(i).printMenuItemInfo();
                }
                System.out.println("[ " + "Total" + " ]");
                System.out.printf("W %.1f%n%n", cart.getTotalPrice());
                System.out.println("1. 주문    2. 메뉴판");
            }
            else if (choice == category.size() + 2) { //주문을 종료
                isProcessing = false;
                throw new ContinueException();
            }
        } else {
            categorySave = choice-1;
            category.get(categorySave).printMenuList(); //카테고리 내 메뉴 선택지 출력
        }
    }

    //step 3: 장바구니에 추가 여부 or 결제 시 할인 정보 입력
    public void step3(int choice) {
        if (isOrdering) {
            if(choice == 1){
                menuItemSave = choice-1;
                //결제 시 할인 정보 입력
                System.out.println("할인 정보를 입력해주세요.");
                int i = 1;
                for(DiscountType discountType : DiscountType.values()){
                    System.out.printf("%d. %-7s : %d%%%n", i, discountType.getName(), discountType.getRate());
                    i++;
                }
            }
            else if(choice ==2){
                isOrdering = false;
                step = 1;
                throw new ContinueException();
            }
        } else {
            //선택된 주문 출력 후, 장바구니에 추가할지 여부 묻기
            menuItemSave = choice-1;
            category.get(categorySave).getMenuItem(menuItemSave).printMenuItemInfo();
            System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
            System.out.println("1. 확인    2. 취소");
        }
    }

    //step 4: 장바구니에 넣기 or 결제하기
    public void step4(int choice) {
        if (isOrdering) {
            //할인이 적용된 금액에 따라 결제
            cart.applyDiscount(DiscountType.fromIndex(choice-1).getRate());
            System.out.printf("주문이 완료되었습니다. 금액은 W %.1f 입니다.%n", cart.getTotalPrice());
            isProcessing = false;
        } else {
            step = 1;
            if (choice == 1) { //장바구니에 넣기
                cart.addItemToCart(category.get(categorySave).getMenuItem(menuItemSave));
                System.out.printf("%n아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.%n%n");
            }
        }
    }

    //main에서 관리하던 입력과 반복문 로직 관리
    public void start() {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        //반복문을 이용해 0 입력 시, 프로그램 종료
        while (isProcessing) {
            try {
                //단계에 따른 출력
                switch (step) {
                    case 1:
                        step1();
                        break;
                    case 2:
                        step2(choice);
                        break;
                    case 3:
                        step3(choice);
                        break;
                    case 4:
                        step4(choice);
                        continue;
                    default:
                        break;
                }

                //입력 받기 & 개행 문자 제거
                choice = input.nextInt();
                input.nextLine();
                System.out.println();

                //입력 확인
                checkValidInput(choice);

            } catch (InputMismatchException e) { //예외 처리
                System.out.println("잘못된 형식의 입력입니다.");
                input.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("해당 값은 선택할 수 없습니다.");
                if(step == 2){ //각 단계에 따라 이전 선택지 복원
                    choice = categorySave+1;
                }
                else if(step == 3){
                    choice = menuItemSave+1;
                }
            } catch (ContinueException e){
                //빠져나오기 용
            }
            catch (Exception e) {
                System.out.println("알 수 없는 오류입니다." + e);
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }
}
