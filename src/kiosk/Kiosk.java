package kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Kiosk {
    //MenuItem을 관리하는 리스트
    private List<Menu> category;
    //장바구니 기능
    private Cart cart;
    //Kiosk 객체 생성 시, 값을 넘겨줌
    public Kiosk(){
        category = new ArrayList<>();
        cart = new Cart();
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

    public void printCategoryList(){
        System.out.println("[ " + "MAIN MENU" + " ]");
        for(int i = 1; i < category.size()+1; i++){
            System.out.printf("%d. %s%n", i, category.get(i-1).getCategory());
        }
        System.out.println("0. 종료");
    }

    public void printMenuItem(MenuItem item){
        String name = item.getName();
        Double price = item.getPrice();
        String desc = item.getDescription();
        System.out.printf("%-15s| W %.1f | %s%n", name, price, desc);
    }

    public void printMenuList(Menu menu){
        System.out.println("[ " + menu.getCategory()+ " MENU" + " ]");
        for(int i = 1; i < menu.getListSize()+1; i++){
            System.out.printf("%d. ", i);
            printMenuItem(menu.getMenuItem(i-1));
        }
        System.out.println("0. 뒤로가기");
    }

    public void printOrderMenu(){
        System.out.println("[ " + "ORDER MENU" + " ]");
        System.out.printf("%d. %-15s| %s%n", category.size()+1, "Orders", "장바구니를 확인 후 주분합니다.");
        System.out.printf("%d. %-15s| %s%n", category.size()+2, "Cancel", "진행중인 주문을 취소합니다.");
    }
    public void askToAddToCart(){
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인    2. 취소");
    }

    //main에서 관리하던 입력과 반복문 로직 관리
    public void start(){
        Scanner input = new Scanner(System.in);
        int choice = -1;
        int step = 1;
        int categoryIndex = 0;
        int menuItemIndex = 0;
        //반복문을 이용해 0 입력 시, 프로그램 종료
        do {
            try {
                //출력
                switch(step){
                    case 1 : //카테고리 선택 단계
                        printCategoryList();
                        if(cart.getCartSizes() > 0){
                            printOrderMenu();
                        }
                        break;
                    case 2 : //카테고리 내의 메뉴 선택 단계
                        categoryIndex = choice - 1;
                        printMenuList(category.get(categoryIndex));
                        break;
                    case 3 : //선택 메뉴 출력 및 장바구니에 넣을 건지 묻기
                        if(choice > category.get(categoryIndex).getListSize()){
                            throw new IndexOutOfBoundsException();
                        }
                        menuItemIndex = choice-1;
                        printMenuItem(category.get(categoryIndex).getMenuItem(menuItemIndex));
                        askToAddToCart();
                        break;
                    case 4 : //장바구니에 넣기
                        if(choice == 1){
                            cart.addItemToCart(category.get(categoryIndex).getMenuItem(menuItemIndex));
                        }
                        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
                        step = 1;
                        continue;
                    default:
                        break;
                }
                //입력 받기 & 개행 문자 제거
                choice = input.nextInt();
                input.nextLine();
                System.out.println();
                //0을 눌렀는데 step이 0이 아닌 경우
                if(choice==0 && step > 0){
                    step--; //step이 1 감소, 1이었을 경우 0으로 되며 반복 종료
                }
                else{
                    step++; //다음 단계로 넘어감
                }

            } catch (InputMismatchException e) {
                System.out.println("잘못된 형식의 입력입니다.");
                input.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("해당 값은 선택할 수 없습니다.");
                choice = categoryIndex+1; //이전에 선택한 카테고리 유지
                step--; //현재 단계 유지
            }
            catch (Exception e) {
                System.out.println("알 수 없는 오류입니다." + e);
            }
        } while (choice != 0 || step != 0);
        System.out.println("프로그램을 종료합니다.");
    }
}
