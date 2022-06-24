package com.clark.blogxcommon.test;

import java.time.LocalDateTime;
import java.util.Date;

public class 时间比较 {
    public static void main(String[] args) {
        LocalDateTime a = LocalDateTime.now();
        LocalDateTime b = a;
        System.out.println(a.isAfter(b));
        System.out.println(a.isBefore(b));

        Date c = new Date();
        Date d = c;
        System.out.println(c.after(d));
        System.out.println(c.before(d));
    }
}
