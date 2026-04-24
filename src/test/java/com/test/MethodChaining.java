package com.test;

public class MethodChaining {
    public static void main(String[] args){
      a1().a2().a3();
    }
    public static MethodChaining a1(){
        System.out.println("mthod1");
        return new MethodChaining();
                 }
    public  MethodChaining a2(){
        System.out.println("mthod2");
        return this;
    }
    public  MethodChaining a3(){
        System.out.println("mthod3");
        return this;
    }
}
