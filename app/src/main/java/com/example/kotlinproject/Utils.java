package com.example.kotlinproject;


import android.util.Log;

public  class Utils {
    public static String getText(){
        return null;
    }

    /**
     * 访问Kotlin的方法
     * 如何调用 lamdaExpress() 函数？
     * @return
     */
    public static String getKotlinMethod(){
        //不能对CommonActivityKt实例化, 因为其字节码内部没有构造函数
//        CommonActivityKt commonActivityKt = new CommonActivityKt();
        return ""+ CommonActivityKt.getInitAge();
    }

    /**
     * 获取内部类和静态内部类的方法
     */
    public static void getJavaInnerMethod(){
        OutterClass outterClass = new OutterClass();
        Log.i("xiongliang","打印country="+ OutterClass.counrty);
        OutterClass.print1();
        outterClass.print4();

        //访问内部类
        OutterClass.InnnerClass innnerClass = outterClass.new InnnerClass();
        innnerClass.getInnerMethod();
        //假设可以定义静态
//        OutterClass.InnnerClass.print2();
        Log.i("xiongliang","打印内部类的类型"+OutterClass.InnnerClass.class);

        //访问静态内部类
        OutterClass.StaticInnerClass staticInnerClass = new OutterClass.StaticInnerClass();
        //访问非静态属性
        staticInnerClass.getCountry();
        //访问静态属性
        OutterClass.StaticInnerClass.print3();
        staticInnerClass.print3();

    }
}
