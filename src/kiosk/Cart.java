package kiosk;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<MenuItem> saveCart;
    private double totalPrice;
    //생성자
    public Cart(){
        this.saveCart = new ArrayList<>();
    }
    //getter
    public int getCartSize(){
        return this.saveCart.size();
    }
    public MenuItem getCartItem(int index){
        return this.saveCart.get(index);
    }

    public void calcTotalPrice(){
        totalPrice = 0.0;
        for(int i = 0; i < saveCart.size(); i++){
            totalPrice += saveCart.get(i).getPrice();
        }
    }
    public double getTotalPrice(){
        return totalPrice;
    }

    //setter
    public void addItemToCart(MenuItem item){
        this.saveCart.add(item);
        System.out.println(item.getName() + "이 장바구니에 추가되었습니다.");
        calcTotalPrice(); //장바구니에 아이템을 추가할 떄마다 최종 결제 금액 업데이트
    }
    public void applyDiscount(int discountRate){
        totalPrice -= totalPrice/100*discountRate;
    }
    public void deleteItemInCart(int index){
        this.saveCart.remove(index);
    }
}
