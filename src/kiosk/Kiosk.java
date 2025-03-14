package kiosk;

import com.sun.jdi.ObjectReference;
import kiosk.domain.Menu;
import kiosk.utill.Cart;
import kiosk.utill.DiscountType;
import kiosk.utill.Order;
import kiosk.utill.Pay;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class ContinueException extends RuntimeException{} //메서드 내에서 continue와 같은 효과를 내기 위한 예외 생성
class ContinueExceptionSetChoice extends RuntimeException{} //예외 처리 시, choice를 다시 설정해야하는 경우
public class Kiosk {

    private List<Menu> category;    //MenuItem을 관리하는 리스트
    private Order order;            //주문받는 기능을 담당하는 클래스
    private Pay pay;                //장바구니를 포함한 결제 기능을 담당하는 클래스

    //현재 키오스크의 상태를 나타내는 변수
    private boolean isProcessing;   //프로그램이 동작 중인가
    private boolean isPaying;       //결제 진행 단계인가
    private boolean isDelete;       //삭제 진행 단계인가
    private int step;               //단계

    //선택 저장 변수 - 예외 발생 또는 이전 단계로 돌아갈 때, 저장된 상태로 돌아가도록 한다.
    int chooseCategory = 0;     //메뉴 선택 단계에 대해, 이전 카테고리 인덱스 저장
    int chooseMenuItem = 0;     //메뉴 선택 단계에 대해, 이전 메뉴아이템 인덱스 저장
    int choosePayProcess = 0;   //결제 진행 단계에 대해, 이전 선택지를 저장하는 변수

    //Kiosk 객체 생성
    public Kiosk() {
        category = new ArrayList<>();
        order = new Order();
        pay = new Pay();

        isProcessing = true;
        isPaying = false;
        isDelete = false;
        step = 1;
    }

    //setter
    public void addCategory(Menu menu) {
        this.category.add(menu);
    }

    //유효한 입력인지 확인하는 메서드
    public void checkValidInput(int minInput, int maxInput, int input){
        if(input >= minInput && input <= maxInput){ //주어진 범위 내의 입력값인가?
            if(input == 0){ //0 입력 시 뒤로가기
                step--;
                if(step == 1){ //step1일 때, 0이 입력되면 종료
                    isProcessing = false;
                }
                else{
                    step = 1;
                }
                throw new ContinueException();
            }
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }

    //예외 발생으로 이전 단계 복귀 시, 이전 선택지 복원
    public int setChoice(int choice){
        if (isPaying) {
            return choosePayProcess;
        }
        switch (step){
            case 2 :
                return chooseCategory + 1;
            case 3 :
                return chooseMenuItem+1;
        }
        return choice;
    }
    //step1 : 카테고리 선택지(+Order Menu 선택지)
    public void step1() {
        //메인 메뉴 출력
        order.printMainMenu(category);
        //장바구니가 비어있지 않다면, 결제를 위한 선택지 출력
        if (!pay.cartIsEmpty()) {
            pay.askStartPaying(category.size());
        }
    }

    //step 2: 카테고리 내 메뉴 선택지 or 장바구니 정보 출력 후 선택지
    public void step2(int choice) {
        checkValidInput(0, category.size()+2, choice);
        if (!pay.cartIsEmpty() && choice > category.size()) { //장바구니에 메뉴가 들어있다면 주문 가능한 상태
            if (choice == category.size()+1) {
                isPaying = true; //결제 진행 중인 상태로 변경
                choosePayProcess = choice;
                pay.askPayingProcess(); //장바구니 정보 출력 후 선택지
                step++;
            }
            else if (choice == category.size() + 2) { //키오스크 종료
                isProcessing = false;
                throw new ContinueExceptionSetChoice();
            }
        }
        else { //0부터 5까지 받아도 5입력되면 자동으로 그거 할 걸?
            chooseCategory = choice - 1;
            order.printCategoryMenu(category.get(chooseCategory)); //카테고리 내 메뉴 선택지 출력
            step++;
        }
    }

    //step 3: 장바구니에 추가 여부 or 결제 시 할인 정보 입력
    public void step3(int choice) {
        if (isPaying) {
            checkValidInput(1, 3, choice);
            step++;
            choosePayProcess = choice;
            switch (choice){
                case 1 :
                    pay.askDiscount();  //할인 유형 묻기
                    break;
                case 2 :
                    step = 1;           //메인 메뉴로 돌아가기
                    isPaying = false;   //결제 중인 상황 아님
                    throw new ContinueExceptionSetChoice();
                case 3:
                    pay.askDeleteItem(); //어떤 아이템을 삭제할건지 묻기
                    isDelete = true;
                    break;
                default:
                    break;
            }
        }
        else {
            checkValidInput(0, category.get(chooseCategory).getListSize(), choice);
            switch(choice){
                case 0:
                    break;
            }
            step++;
            chooseMenuItem = choice-1;
            //선택된 주문 출력 후, 장바구니에 추가할지 여부 묻기
            order.askChooseItem(category.get(chooseCategory).getMenuItem(chooseMenuItem));
        }
    }

    //step 4: 장바구니에 넣기 or 결제하기 or 삭제하기
    public void step4(int choice) {
        if (isPaying) {
            if(isDelete){
                checkValidInput(1, pay.cartSize(), choice);
                pay.deleteItem(choice-1);
                isDelete = false;  //삭제 단계 아님으로 변경
                if(pay.cartIsEmpty()){ // 삭제 후, 장바구니가 비어있다면
                    step = 1;          //메인 메뉴 출력으로 돌아가기
                    isPaying = false;  //결제 진행 중이 아닌 상태로 변경
                    throw new ContinueException();
                }
                else{                       //장바구니가 비어있지 않다면
                    pay.askPayingProcess(); //장바구니 정보 출력 후 선택지
                    step = 3;
                }
            }
            else{
                //할인이 적용된 금액에 따라 결제
                pay.completePaying(DiscountType.fromIndex(choice-1).getRate());
                isProcessing = false;
                throw new ContinueExceptionSetChoice();
            }
        } else {
            checkValidInput(1, 2, choice);
            step = 1;
            if (choice == 1) { //장바구니에 넣기
                pay.addItemToCart(category.get(chooseCategory).getMenuItem(chooseMenuItem));
                System.out.printf("%n아래 메뉴판을 보고 메뉴를 골라 입력해주세요.%n%n");
            }
            throw new ContinueExceptionSetChoice();
        }
    }

    //main에서 관리하던 입력과 반복문 로직 관리
    public void start() {
        Scanner input = new Scanner(System.in);
        //반복문을 이용해 0 입력 시, 프로그램 종료
        int choice = 0;
        while (isProcessing) {
            try {
                //단계에 따른 출력
                switch (step) {
                    case 1:
                        step1();
                        step++;
                        break;
                    case 2:
                        step2(choice);
                        break;
                    case 3:
                        step3(choice);
                        break;
                    case 4:
                        step4(choice);
                    default:
                        break;
                }
                //입력 받기 & 개행 문자 제거
                choice = input.nextInt();
                input.nextLine();
                System.out.println();
            } catch (InputMismatchException e) { //예외 처리
                System.out.printf("잘못된 형식의 입력입니다.%n%n");
                input.nextLine();
                step--;
                choice = setChoice(choice);
            } catch (IndexOutOfBoundsException e) {
                System.out.printf("해당 값은 선택할 수 없습니다.%n%n");
                step--;
                choice = setChoice(choice);
            } catch (ContinueException e){
                //빠져나오기 용
            } catch (ContinueExceptionSetChoice e) {
                //choice를 재설정하는 빠져나오기
                choice = setChoice(choice);
            }
            catch (Exception e) {
                System.out.println("알 수 없는 오류입니다." + e);
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }
}
