package kiosk;

import java.util.*;

public class Cart {
    private LinkedHashMap<MenuItem, Integer> saveCart;
    private double totalPrice;
    //생성자
    public Cart(){
        this.saveCart = new LinkedHashMap<>();
        totalPrice = 0.0;
    }

    //getter
    public int getCartSize(){
        return this.saveCart.size();
    }
    public double getTotalPrice(){
        return totalPrice;
    }

    //setter
    public void addItemToCart(MenuItem item){
        saveCart.compute(item, (key, value) -> (value == null) ? 1 : value + 1);
        System.out.println(item.getName() + "이 장바구니에 추가되었습니다.");
        calcTotalPrice(); //장바구니에 아이템을 추가할 떄마다 최종 결제 금액 업데이트
    }

    //결제 금액의 합계 계산
    public void calcTotalPrice(){
        Iterator<MenuItem> iterator = saveCart.keySet().iterator();
        totalPrice = 0.0;
        for(int i = 0; i < saveCart.size(); i++){
            MenuItem item = iterator.next();
            double price = item.getPrice();
            int num = saveCart.get(item);
            totalPrice += price * num;
        }
    }

    //할인 적용
    public void applyDiscount(int discountRate){
        totalPrice -= totalPrice/100*discountRate;
    }

    //메뉴 제거
    public void deleteItemInCart(int index){
        MenuItem keyToRemove = saveCart.keySet()
                .stream()
                .skip(index - 1) // 사용자가 1부터 입력한다고 했으므로 -1
                .findFirst()
                .orElse(null);
        if (keyToRemove != null) {
            saveCart.remove(keyToRemove);
            System.out.println(keyToRemove.getName()+"가 삭제 되었습니다.");
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
        System.out.println("[ " + "Total" + " ]");
        System.out.printf("W %.1f%n%n", totalPrice);
    }
}
