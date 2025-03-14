package kiosk.utill;

import kiosk.domain.MenuItem;

import java.util.*;

public class Cart {
    private LinkedHashMap<MenuItem, Integer> saveCart;
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
        saveCart.compute(item, (key, value) -> (value == null) ? 1 : value + 1);
        System.out.println(item.getName() + "이 장바구니에 추가되었습니다.");
    }

    //장바구니 예상 결제 금액 계산
    public double calcTotalPrice(){
        Iterator<MenuItem> iterator = saveCart.keySet().iterator();
        double totalPrice = 0.0;
        for(int i = 0; i < saveCart.size(); i++){
            MenuItem item = iterator.next();
            double price = item.getPrice();
            int num = saveCart.get(item);
            totalPrice += price * num;
        }
        return totalPrice;
    }

    //메뉴 제거
    public void deleteItemInCart(int index){
        MenuItem keyToRemove = saveCart.keySet()
                .stream()
                .skip(index)
                .findFirst()
                .orElse(null);
        if (keyToRemove != null) {
            saveCart.remove(keyToRemove);
            System.out.println(keyToRemove.getName()+"가 삭제 되었습니다.\n");
            calcTotalPrice();
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
