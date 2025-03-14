package kiosk.utill;

import kiosk.domain.MenuItem;

//장바구니 및 결제 과정을 관리하는 클래스
public class Pay {
    private Cart cart; //장바구니
    private double totalPrice;
    //생성자
    public Pay(){
        cart = new Cart();
        totalPrice = 0.0;
    }

    //getter
    public int cartSize(){
        return cart.getCartSize();
    }

    public boolean cartIsEmpty(){
        if(cart.getCartSize() > 0){
            return false;
        }
        else{
            return true;
        }
    }

    //setter
    public void addItemToCart(MenuItem menuItem){
        cart.addItemToCart(menuItem);
        totalPrice = cart.calcTotalPrice();
    }

    //----- 질문 메서드 -----
    public void askStartPaying(int num){
        System.out.printf("%n[ " + "ORDER MENU" + " ]%n");
        System.out.printf("%d. %-15s| %s%n",  num+1, "Orders", "장바구니를 확인 후 주문합니다.");
        System.out.printf("%d. %-15s| %s%n", num+2, "Cancel", "진행중인 주문을 취소합니다.");
    }

    public void askPayingProcess(){
        System.out.println("아래와 같이 주문하시겠습니까?");
        cart.printCartList();
        printTotalPrice();
        System.out.println("1. 주문    2. 메뉴판    3. 삭제");
    }

    public void askDiscount(){
        System.out.println("할인 정보를 입력해주세요.");
        int i = 1;
        for(DiscountType discountType : DiscountType.values()){
            System.out.printf("%d. %-7s : %d%%%n", i, discountType.getName(), discountType.getRate());
            i++;
        }
    }

    public void askDeleteItem(){
        cart.printCartList();
        System.out.println("삭제할 메뉴의 번호를 입력해주세요");
    }

    //결제 금액 출력 메서드
    public void printTotalPrice(){
        System.out.println("[ " + "Total" + " ]");
        System.out.printf("W %.1f%n%n", totalPrice);
    }

    //----- 기능 관련 메서드 -----
    //삭제 메서드
    public void deleteItem(int index){
        cart.deleteItemInCart(index);   //아이템 삭제
        totalPrice = cart.calcTotalPrice();
        if(cart.getCartSize() > 0) {
            cart.printCartList();           //삭제 후 장바구니 상태 출력
            printTotalPrice();
        }
    }
    //할인 적용 메서드
    public void applyDiscount(int discountRate){
        totalPrice -= totalPrice/100*discountRate; //할인을 적용한 금액 계산 및 저장
    }
    //결제 완료 메서드
    public void completePaying(int index){
        applyDiscount(index); //할인 적용
        System.out.printf("주문이 완료되었습니다. 금액은 W %.1f 입니다.%n", totalPrice);
    }
}
