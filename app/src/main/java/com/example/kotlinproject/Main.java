package com.example.kotlinproject;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String args[]){
//        Utils.getJavaInnerMethod();
        try{
            CommonActivityKt.kotlinThrowException();
        }catch (FileNotFoundException exception){
            System.out.println("打印 FileNotException...");
        }
    }
}
