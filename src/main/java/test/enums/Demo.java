package test.enums;

public class Demo {

    public static void main(String[] args) {
        MaYun my=MaYun.himself;
        MaYun mc=MaYun.himself;
        MaYun my1=MaYun.m2;
        my.setALlAPI("123");
        my.pNM();
        mc.pNM();
        my1.pNM();   
        System.out.println(my.equals(mc));
        mc.setALlAPI("321");
        my.pNM();
        mc.pNM();
        my1.pNM();  
        my1.setALlAPI("456");
        my.pNM();
        mc.pNM();
        my1.pNM();
    }
}
