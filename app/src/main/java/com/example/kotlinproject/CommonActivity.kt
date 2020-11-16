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
//        method2()
//        method3()
//        method4()
        method5()
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

    /**
     * 协变和逆变
     */
    fun method3(){
        var home1 = Home1()
        home1.testGeneric2()
    }

    /***
     * 处协变和逆变
     */
    fun method4(){
        var home1 = Home1()
        //声明处协变和逆变
        var to:Array<Int> = arrayOf(1,2,3,4)
        var form:Array<Any> = arrayOf("hello","hello","hello","hello")
        home1.copy(to,form)
        for (item in form){
            Log.i("xiongliang","打印 out  item="+item)
        }

        Log.i("xiongliang","----------------")

        var array1:Array<Any> = arrayOf("hello","hello","hello","hello")
        home1.setArrayValue(array1,1,"2")
        for (item in array1){
            Log.i("xiongliang","打印 out  item="+item+".."+item.javaClass.typeName)
        }
    }

    /**
     * 对象表达式使用
     */
    fun method5(){
        var home1 = Home1()
        home1.objectExpress()
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
    }

    fun calucate(calucator: AddCalucator){
        when(calucator){
            is AddCalucator -> {

            }
        }
    }


    fun ChildStudent.mutifyCalucate(a:Int,b:Int) = a*b

    /**
     * 测试泛型 协变 和 逆变
     */
//    fun testGeneric(){
//         //验证协变
//         var animalOne = mutableListOf<Animal>()
//         animalOne.add(Cat())
//         var animalTwo:MutableList<out Animal> = animalOne
//         //由于添加类型是Animal以及其子类, 添加的可能是Cat,也可能是Dog, 最后导致泛型混乱.
//         animalTwo.add(Cat())
//         animalTwo.add(Animal())
//         var result:Animal = animalTwo.get(0) //根据多态的向上转型
//
//        //验证逆变
//        var animalThree = mutableListOf<Animal>()
//        animalThree.add(Cat())
//        var animalFour:MutableList<in Animal> = animalOne
//        animalFour.add(Cat())
//        animalFour.add(Animal())
//        var result2:Animal =animalFour.get(0)
//    }

    /**
     * 协变和逆变
     *
     */
    fun testGeneric2(){
        //out 协变 可以将子类型 赋值 给父类型引用
        var producer1:Producer<Animal> = DogProducer()
        var producer2:Producer<Animal> = CatProducer()
        var producer3:Producer<Animal> = YelloCatProductor()

        Log.i("xiongliang","打印producer1="+producer1.produce())
        Log.i("xiongliang","打印producer2="+producer2.produce())
        Log.i("xiongliang","打印producer3="+producer3.produce())

        Log.i("xiongliang","------------------------------")
        //in 逆变 可以将父类型对象赋值给子类型引用
        var comsumer1:Comsumer<YelloCat> = Huaman()
        var comsumer2: Comsumer<YelloCat> = Men()
        var comsumer3:Comsumer<YelloCat> = Boy()
        comsumer1.consumer(YelloCat())
        comsumer2.consumer(YelloCat())
        comsumer3.consumer(YelloCat())
    }

    /**
     * 泛型使用处声明
     */
    fun  copy(form:Array<out Any>,to:Array<Any>){
        if(form.size == to.size){
            for (i in form.indices){
                to[i] = form[i]
            }
        }
    }

    fun setArrayValue(array:Array<in String>,index:Int,value:String){
        if(index < array.size && index >= 0){
            array[index] = value
        }
    }

    /***
     * 对象表达式
     */

    private var object2 = object{
        var expressName:String
        init {
           expressName = ""
        }
    }


    fun objectExpress(){
       var name:String= ""
       var object1 = object :ObjectInterface, ObjectInterface1() {
           override fun print() {
               name = "test"
               Log.i("xiongliang","执行print"+name)
           }

           override fun printAbstract() {
               Log.i("xiongliang","执行printAbstract ")
           }
       }
       Log.i("xiongliang","打印 对象表达式作为成员变量="+object2.expressName)
       object1.print()
       object1.printAbstract()
    }
}


data class Person(
    var name:String,
    val age:Int
)

sealed class Calucator

class AddCalucator() :Calucator()



open class Animal
class Dog:Animal()
open class Cat :Animal()
class YelloCat:Cat()

interface Producer<out T>{
    fun produce():T
}
interface Comsumer<in T>{
    fun consumer(item:T)
}

class DogProducer:Producer<Dog>{
    override fun produce(): Dog {
        return Dog()
    }
}
class CatProducer:Producer<Cat>{
    override fun produce(): Cat {
        return Cat()
    }
}

class YelloCatProductor:Producer<YelloCat>{
    override fun produce(): YelloCat {
        return YelloCat()
    }
}


class Huaman:Comsumer<Animal>{
    override fun consumer(item: Animal) {
        Log.i("xiongliang","接受到item="+item)
    }
}

class Men:Comsumer<Cat>{
    override fun consumer(item: Cat) {
        Log.i("xiongliang","接受到item="+item)
    }

}

class Boy:Comsumer<YelloCat>{
    override fun consumer(item: YelloCat) {
        Log.i("xiongliang","接受到item="+item)
    }
}

class OutClass{
    var name:String = ""
    //嵌套类
    class NestClass{
        fun foo(){
            Log.i("xiongliang","嵌套类=")
        }
    }
    //内部类
    inner class InnerClass{
        fun foo(){
            Log.i("xiongliang","内部类"+ this@OutClass.name)
        }
    }
}


//对象表达式
interface ObjectInterface{
    fun print()
}

abstract class ObjectInterface1{
    abstract fun printAbstract()
}



