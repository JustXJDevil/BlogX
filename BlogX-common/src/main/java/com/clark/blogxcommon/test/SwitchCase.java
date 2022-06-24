package com.clark.blogxcommon.test;

public class SwitchCase {

    public static void main(String[] args) {
        Integer a = buildEducation("小学");
        Integer b = buildEducation(null);
        int ab = 1;
    }

    private static Integer buildEducation(String education) {
        if (education == null) {
            return null;
        }
        /**
         * 教育程序：1小学，2初中，3高中，4大专，5大学，6研究生，7博士
         */
        switch (education) {
            case "小学":
                return 1;
            case "初中":
                return 2;
            case "高中":
                return 3;
            case "大专":
                return 4;
            case "大学":
                return 5;
            case "研究生":
                return 6;
            case "博士":
                return 7;
            default:
                return null;
        }
    }
}
