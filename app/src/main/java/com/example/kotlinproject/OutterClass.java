package com.example.kotlinproject;

import android.util.Log;

/***
 * 成员内部类:
 * 外部类实例的部分,依托外部实例存在,与实例变量、实例方法同级别;
 * 成员内部类不能定义静态成员.
 *
 * 静态内部类(相当与外部类):
 * 静态内部类可以定义静态成员。
 * 不依赖外部类对象, 可直接通过类名访问静态成员, 实例成员通过外部类实例访问.
 *
 *
 *
 * https://www.bilibili.com/video/BV1wK411L7Gj?from=search&seid=2930705343392550931
 */
public class OutterClass {
    private String name = "xiongliang";


    public static void print1(){

    }

    public void print4(){
    }


    /**
     * 非静态内部类
     */
    class InnnerClass{
        private int age =29;

        public void getInnerMethod(){
            print4();
            Log.i("xiongliang","获取内部类的方法");
        }

//        public static void print2(){
//
//        }
    }

    /**
     * 内部类
     */
    static class StaticInnerClass{
         private String country = "wuhan";

         public static void print3(){
             print1();
         }

         public final  void getCountry(){
             print1();
             Log.i("xiongliang","获取城市="+country);
         }
    }
}
