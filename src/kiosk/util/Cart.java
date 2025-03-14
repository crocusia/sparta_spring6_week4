package kiosk.util;

import kiosk.domain.MenuItem;

import java.util.*;

public class Cart {
    private LinkedHashMap<MenuItem, Integer> saveCart; //삭제를 위해 순서가 보장되는 LinkedHashMap 사용
    //생성자
    public Cart(){
        this.saveCart = new LinkedHashMap<>();
    }

    //getter
    public int getCartSize(){
        return this.saveCart.size();
    }

    //setter
    public void addItemToCart(MenuItem item){
        //Key(메뉴아이템)가 일치하면 해당 키의 값만 1 증가, 아니라면 (메뉴아이템, 1) add
        saveCart.compute(item, (key, value) -> (value == null) ? 1 : value + 1);
        System.out.println(item.getName() + "이 장바구니에 추가되었습니다.");
    }

    //장바구니 예상 결제 금액 계산
    public double calcTotalPrice(){
        Iterator<MenuItem> iterator = saveCart.keySet().iterator(); //반복자 생성
        double totalPrice = 0.0;
        for(int i = 0; i < saveCart.size(); i++){   //장바구니의 크기만큼 반복(while문 사용해도 무방)
            MenuItem item = iterator.next();        //반복자에 따른 값을 가져옮
            totalPrice += item.getPrice() * saveCart.get(item); //가격과 수량을 곱한 금액을 총합에 더함
        }
        return totalPrice;  //합계를 반환
    }

    //메뉴 제거
    public void deleteItemInCart(int index){
        MenuItem keyToRemove = saveCart.keySet()        //장바구니의 모든 키를 순서대로 가져오기
                .stream()                               //스트림으로 변환
                .skip(index)                            //n개의 요소를 건너뜀
                .findFirst()                            //n개 만큼 건너뛴 후 요소가 남아있다면 첫번째를 반환
                .orElse(null);                    //값이 없을 경우 null 반환
        if (keyToRemove != null) {                      //메뉴아이템이 반환된 경우
            saveCart.remove(keyToRemove);               //해당 아이템 삭제
            System.out.println(keyToRemove.getName()+"가 삭제 되었습니다.\n");
            calcTotalPrice();                           //총 금액 다시 계산
        } else {
            System.out.println("삭제할 항목을 찾을 수 없습니다.");
        }
    }

    //장바구니 내역 출력
    public void printCartList(){
        System.out.println("[ " + "Orders" + " ]");
        Iterator<MenuItem> iterator = saveCart.keySet().iterator();
        for(int i = 1; i <= saveCart.size(); i++){
            MenuItem item = iterator.next();
            System.out.println(i + ". " + item + " | 수량 : " + saveCart.get(item));
        }
    }
}
