package com.example.kotlinproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * 基础语法练习
 */
class CommonActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        method1()
        method2()
    }

    /**
     * 遍历数组
     */
    fun method1(){
       var array = intArrayOf(1,2,4,5)
       var strings = arrayListOf<String>("hello","world","list","test")
       for (i in array.indices){
           Log.i("xiongliang","打印index="+i+"value="+array[i])
       }

        for ((index,value) in array.withIndex()){
            Log.i("xiongliang","打印index="+index+"value="+value)
        }

        when{
            3 in array->{
                Log.i("xiongliang","打印 3 在数组中")
            }
            else->{
                Log.i("xiongliang","打印 不在数组中")
            }
        }

        strings.filter { it.length >5 }.map { it.toUpperCase() }.sorted().forEach{
            Log.i("xiongliang","打印过滤字符串="+it)
        }
    }

    /**
     * 类构造函数的使用
     */
    fun method2(){
       var emptyClass = EmptyClass("名字")
       emptyClass.printUserName()
    }

}


class EmptyClass(userName:String){
    private var userName = userName

    init {
        Log.i("xiongliang","打印userName="+userName)
    }

    fun printUserName(){
        Log.i("xiongliang","方法调用="+userName)
    }

}