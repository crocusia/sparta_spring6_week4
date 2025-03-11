package kiosk;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private boolean cartStatus;
    private List<MenuItem> saveCart;
    //생성자
    public Cart(){
        this.saveCart = new ArrayList<>();
        this.cartStatus = false;
    }
    //getter
    public int getCartSizes(){
        return this.saveCart.size();
    }
    public MenuItem getCartItem(int index){
        return this.saveCart.get(index);
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
