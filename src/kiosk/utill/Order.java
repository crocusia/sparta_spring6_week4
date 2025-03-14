package kiosk.utill;

import kiosk.domain.Menu;
import kiosk.domain.MenuItem;

import java.util.List;

//주문 과정을 관리하는 클래스
public class Order {
    //메인 메뉴 출력
    public void printMainMenu(List<Menu> array){
        System.out.println("[ " + "MAIN MENU" + " ]");
        for (int i = 1; i < array.size() + 1; i++) {
            System.out.printf("%d. %s%n", i, array.get(i - 1).getCategory());
        }
        System.out.println("0. 종료");
    }

    public void printCategoryMenu(Menu menu){
        menu.printMenuList();
        System.out.println("0. 뒤로가기");
    }

    public void askChooseItem(MenuItem item){
        System.out.println(item);
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인    2. 취소");
    }

}
