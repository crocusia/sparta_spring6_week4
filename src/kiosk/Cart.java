package kiosk;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<MenuItem> saveCart;
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
    public double getTotalPrice(){
        double total = 0.0;
        for(int i = 0; i < saveCart.size(); i++){
            total += saveCart.get(i).getPrice();
        }
        return total;
    }
    //setter
    public void addItemToCart(MenuItem item){
        this.saveCart.add(item);
        System.out.println(item.getName() + "이 장바구니에 추가되었습니다.");
    }
    public void deleteItemInCart(int index){
        this.saveCart.remove(index);
    }
}
