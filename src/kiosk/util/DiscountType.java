package kiosk.util;

public enum DiscountType {
    PATRIOT(10, "국가유공자"), SOLDIER(5, "군인"), STUDENT(3, "학생"), NORMAL(0, "일반");

    private int rate;
    private String name;
    //생성자
    DiscountType(int rate, String name){
        this.rate = rate;
        this.name = name;
    }
    //getter
    public int getRate(){
        return rate;
    }
    public String getName(){
        return name;
    }
    //index 위치의 DiscountType 반환
    public static DiscountType fromIndex(int index){
        DiscountType[] values = values();
        return values[index];
    }
}
