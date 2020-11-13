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
        var emptyClass1 = EmptyClass("名字",30)
        emptyClass1.printUserName()
    }

}


/**
 * 定义构造函数
 */
class EmptyClass(userName:String){
    private var userName = userName
    private var age:Int
    init {
        this.userName = userName
        this.age = 10
    }

    constructor(userName: String, age:Int):this(userName){
        this.userName = userName
        this.age = age
    }

    fun printUserName(){
        Log.i("xiongliang","方法调用 userName="+userName+"age="+age)
    }
}

/**
 * 直接属性属性
 */
open class Student(var userName:String, var age:Int=2){
    open var name:String = ""

    fun printUserName(){
        Log.i("xiongliang","方法调用 userName="+userName+"age="+age)
    }

    open fun print(){

    }
}


/**
 * 每个sencond 构造方法都需要 初始化父构造方法
 */
class ChildStudent : Student{
    override var name:String = "123"

    constructor(userName: String,age: Int):super(userName,age){

    }

    constructor(userName: String):super(userName,23){

    }

    override  fun print(){

    }

    fun calucate(a:Int,b:Int) = a+b
}

fun ChildStudent.mutifyCalucate(a:Int,b:Int) = a*b


interface Home{
    fun name()
    fun test(){

    }
}

class Home1 :Home{
    lateinit var age:String

    companion object Instance{
        var instanceName:String = ""

        @JvmStatic
        fun getName():String{
            return instanceName
        }
    }

    override fun name() {
        var childStudent = ChildStudent("11")
        childStudent.mutifyCalucate(1,2)

        Home1.Instance.instanceName
    }

    fun ChildStudent.mutifyCalucate(a:Int,b:Int) = a*b
}


