package com.example.kotlinproject;

import kotlin.reflect.KFunction;

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
}
