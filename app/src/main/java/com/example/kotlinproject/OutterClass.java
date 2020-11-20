package com.example.kotlinproject;

import android.util.Log;

/***
 * 成员内部类:
 *
 * 静态内部类:
 *
 * 局部内部类:
 *
 *
 * https://www.bilibili.com/video/BV1wK411L7Gj?from=search&seid=2930705343392550931
 */
public class OutterClass {
    private String name = "xiongliang";


    public static void print(){

    }

    /**
     * 非静态内部类
     */
    class InnnerClass{
        private int age =29;

        public void getInnerMethod(){
            Log.i("xiongliang","获取内部类的方法");
        }

        public static void print(){

        }
    }

    /**
     * 内部类
     */
    static class StaticInnerClass{
         private String country = "wuhan";

         public static void print(){

         }

         public void getCountry(){
             Log.i("xiongliang","获取城市="+country);
         }
    }
}
