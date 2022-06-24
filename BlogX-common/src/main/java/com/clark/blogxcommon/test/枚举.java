package com.clark.blogxcommon.test;

public class 枚举 {
    public static void main(String[] args) {
        System.out.println(Type.mobile.equals("mobile"));;
    }

    public static enum Type{
        mobile,
        card
    }
}
