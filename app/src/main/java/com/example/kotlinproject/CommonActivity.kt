package com.example.kotlinproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * 基础语法练习
 */
class CommonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        method1()
//        method2()
//        method3()
//        method4()
//        method5()
//        method6()
        method7()
    }

    /**
     * 遍历数组
     */
    fun method1() {
        var array = intArrayOf(1, 2, 4, 5)
        var strings = arrayListOf<String>("hello", "world", "list", "test")
        for (i in array.indices) {
            Log.i("xiongliang", "打印index=" + i + "value=" + array[i])
        }

        for ((index, value) in array.withIndex()) {
            Log.i("xiongliang", "打印index=" + index + "value=" + value)
        }

        when {
            3 in array -> {
                Log.i("xiongliang", "打印 3 在数组中")
            }
            else -> {
                Log.i("xiongliang", "打印 不在数组中")
            }
        }

        strings.filter { it.length > 5 }.map { it.toUpperCase() }.sorted().forEach {
            Log.i("xiongliang", "打印过滤字符串=" + it)
        }
    }

    /**
     * 类构造函数的使用
     */
    fun method2() {
        var emptyClass = EmptyClass("名字")
        emptyClass.printUserName()
        var emptyClass1 = EmptyClass("名字", 30)
        emptyClass1.printUserName()
    }

    /**
     * 协变和逆变
     */
    fun method3() {
        var home1 = Home1()
        home1.testGeneric2()
    }

    /***
     * 处协变和逆变
     */
    fun method4() {
        var home1 = Home1()
        //声明处协变和逆变
        var to: Array<Int> = arrayOf(1, 2, 3, 4)
        var form: Array<Any> = arrayOf("hello", "hello", "hello", "hello")
        home1.copy(to, form)
        for (item in form) {
            Log.i("xiongliang", "打印 out  item=" + item)
        }

        Log.i("xiongliang", "----------------")

        var array1: Array<Any> = arrayOf("hello", "hello", "hello", "hello")
        home1.setArrayValue(array1, 1, "2")
        for (item in array1) {
            Log.i("xiongliang", "打印 out  item=" + item + ".." + item.javaClass.typeName)
        }
    }

    /**
     * 对象表达式使用
     */
    fun method5() {
        var home1 = Home1()
        home1.objectExpress()
    }

    /***
     * 测试委托
     */
    fun method6() {
        var home1 = Home1()
        home1.testEntrust()
    }

    /**
     * 测试lamda 表达式
     */
    fun method7(){
        lamdaExpress(1,2){  x,y->
            Log.i("xiongliang","执行lamda 表达式")
            Log.i("xiongliang","打印x+y="+x+y)
        }
        //可变参数
        varargParam("1","2","3")
        varargParam1(params = *arrayOf("1","2"))

        var propertyClass = PropertyClass()
        //中缀方法调用
        propertyClass infixFun(9)

        //高阶函数
        var add:(Int,Int)->Int= {a, b ->  a+b}
        var subtract = {a:Int,b:Int -> a-b}

        Log.i("xiongliang","高阶函数add="+add(1,2))
        Log.i("xiongliang","高阶函数subtract="+subtract(1,2))

        var str = "abcd456fssdf"
        var result = str.filter {
            if(it >= 'a' && it <= 'z'){
                 return@filter true
            }
            return@filter false
        }
        Log.i("xiongliang","打印扩展String = "+result)

        var sum = ""
        var strings = arrayOf("hello","world","helloD","welcome")
        strings.filter { it.contains("d",ignoreCase = true) }.map { it.toUpperCase() }.forEach{
            sum += it
            Log.i("xiongliang","打印字符串="+it)
        }
        //闭包使用
        Log.i("xiongliang","打印闭包操作sum="+sum)

        //函数字面值
        val numSubtract: Int.(other:Int)->Int = {
            other ->  this-other
        }
        Log.i("xiongliang","打印字面值="+1.numSubtract(3))

        //解构声明
        var(name1,age1) = Person("xiongliang",29)
        var(name2,age2) = Pair("xiongliang",20)
        Log.i("xiongliang","打印解构声明  name1= $name1 age1 $age1")
        Log.i("xiongliang","打印解构声明 pair name2= $name2 age2 $age2")
        var map2 = mapOf("a" to "aa","b" to "bb","c" to "cc")
        map2.mapValues { (key,value)-> "$value hello" }.forEach {
            Log.i("xiongliang","解构声明 调用map="+it.value)
        }
    }



}


/**
 * 定义构造函数
 */
class EmptyClass(userName: String) {
    private var userName = userName
    private var age: Int

    init {
        this.userName = userName
        this.age = 10
    }

    constructor(userName: String, age: Int) : this(userName) {
        this.userName = userName
        this.age = age
    }

    fun printUserName() {
        Log.i("xiongliang", "方法调用 userName=" + userName + "age=" + age)
    }
}

/**
 * 直接属性属性
 */
open class Student(var userName: String, var age: Int = 2) {
    open var name: String = ""

    fun printUserName() {
        Log.i("xiongliang", "方法调用 userName=" + userName + "age=" + age)
    }

    open fun print() {

    }
}


/**
 * 每个sencond 构造方法都需要 初始化父构造方法
 */
class ChildStudent : Student {
    override var name: String = "123"

    constructor(userName: String, age: Int) : super(userName, age) {

    }

    constructor(userName: String) : super(userName, 23) {

    }

    override fun print() {

    }

    fun calucate(a: Int, b: Int) = a + b
}

//扩展方法
fun ChildStudent.mutifyCalucate(a: Int, b: Int) = a * b

fun String.filter(predicate: (Char)->Boolean):String{

    var result:StringBuffer = StringBuffer()
    for (index in 0 until length){  //不包括length
        if(predicate(get(index))){
            result.append(get(index))
        }
    }
    return result.toString()
}


interface Home {
    fun name()
    fun test() {

    }
}


class Home1 : Home {
    lateinit var age: String

    companion object Instance {
        var instanceName: String = ""

        @JvmStatic
        fun getName(): String {
            return instanceName
        }
    }

    override fun name() {
        var childStudent = ChildStudent("11")
        childStudent.mutifyCalucate(1, 2)
    }

    fun calucate(calucator: AddCalucator) {
        when (calucator) {
            is AddCalucator -> {

            }
        }
    }

    //扩展方法
    fun ChildStudent.mutifyCalucate(a: Int, b: Int) = a * b

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
    fun testGeneric2() {
        //out 协变 可以将子类型 赋值 给父类型引用
        var producer1: Producer<Animal> = DogProducer()
        var producer2: Producer<Animal> = CatProducer()
        var producer3: Producer<Animal> = YelloCatProductor()

        Log.i("xiongliang", "打印producer1=" + producer1.produce())
        Log.i("xiongliang", "打印producer2=" + producer2.produce())
        Log.i("xiongliang", "打印producer3=" + producer3.produce())

        Log.i("xiongliang", "------------------------------")
        //in 逆变 可以将父类型对象赋值给子类型引用
        var comsumer1: Comsumer<YelloCat> = Huaman()
        var comsumer2: Comsumer<YelloCat> = Men()
        var comsumer3: Comsumer<YelloCat> = Boy()
        comsumer1.consumer(YelloCat())
        comsumer2.consumer(YelloCat())
        comsumer3.consumer(YelloCat())
    }

    /**
     * 泛型使用处声明
     */
    fun copy(form: Array<out Any>, to: Array<Any>) {
        if (form.size == to.size) {
            for (i in form.indices) {
                to[i] = form[i]
            }
        }
    }

    fun setArrayValue(array: Array<in String>, index: Int, value: String) {
        if (index < array.size && index >= 0) {
            array[index] = value
        }
    }

    /***
     * 对象表达式
     */

    private var object2 = object {
        var expressName: String

        init {
            expressName = ""
        }
    }

    /**
     * 对象表达式
     */
    fun objectExpress() {
        var name: String = ""
        var object1 = object : ObjectInterface, ObjectInterface1() {
            override fun print() {
                name = "test"
                Log.i("xiongliang", "执行print" + name)
            }

            override fun printAbstract() {
                Log.i("xiongliang", "执行printAbstract ")
            }
        }
        Log.i("xiongliang", "打印 对象表达式作为成员变量=" + object2.expressName)
        object1.print()
        object1.printAbstract()
    }

    /**
     * 测试枚举
     */
    fun testEnum() {
        var season = Season.values()
        for (item in season) {
            Log.i("xiongliang", "打印season=" + season)
        }
    }

    /**
     * 测试委托
     */
    fun testEntrust() {
        var myEntrust = myEntrust(Entrust()).print()

        var propertyClass = PropertyClass()
        propertyClass.name = "xiongliang"
        Log.i("xiongliang", "属性委托=" + propertyClass.name)

        Log.i("xiongliang","打印延迟属性委托="+ initAge)
        Log.i("xiongliang","打印延迟属性委托="+ initAge)

        propertyClass.age = 29
        Log.i("xiongliang","打印非空属性委托Property.age="+propertyClass.age)

        propertyClass.observableAge = 50
        Log.i("xiongliang","打印可观测属性委托 observableAge="+propertyClass.observableAge)

        var mapDelegate = MapDelegate(mapOf(
            "name" to "xiongliang",
            "age" to 29,
            "address" to "xiaotao"
        ))
        Log.i("xiongliang","打印 name= ${mapDelegate.name} age= ${mapDelegate.age} address=${mapDelegate.address}")
    }

}


data class Person(
    var name: String,
    val age: Int
)

sealed class Calucator

class AddCalucator() : Calucator()


open class Animal
class Dog : Animal()
open class Cat : Animal()
class YelloCat : Cat()

interface Producer<out T> {
    fun produce(): T
}

interface Comsumer<in T> {
    fun consumer(item: T)
}

class DogProducer : Producer<Dog> {
    override fun produce(): Dog {
        return Dog()
    }
}

class CatProducer : Producer<Cat> {
    override fun produce(): Cat {
        return Cat()
    }
}

class YelloCatProductor : Producer<YelloCat> {
    override fun produce(): YelloCat {
        return YelloCat()
    }
}


class Huaman : Comsumer<Animal> {
    override fun consumer(item: Animal) {
        Log.i("xiongliang", "接受到item=" + item)
    }
}

class Men : Comsumer<Cat> {
    override fun consumer(item: Cat) {
        Log.i("xiongliang", "接受到item=" + item)
    }

}

class Boy : Comsumer<YelloCat> {
    override fun consumer(item: YelloCat) {
        Log.i("xiongliang", "接受到item=" + item)
    }
}

class OutClass {
    var name: String = ""

    //嵌套类
    class NestClass {
        fun foo() {
            Log.i("xiongliang", "嵌套类=")
        }
    }

    //内部类
    inner class InnerClass {
        fun foo() {
            Log.i("xiongliang", "内部类" + this@OutClass.name)
        }
    }
}


//对象表达式
interface ObjectInterface {
    fun print()
}

abstract class ObjectInterface1 {
    abstract fun printAbstract()
}


enum class Season(var template: Int) {
    SUMMER(20) {
        override fun print() {
            Log.i("xiongliang", "打印枚举" + SUMMER)
        }
    },

    TEST(20) {
        override fun print() {
            Log.i("xiongliang", "打印枚举" + TEST)
        }
    };

    abstract fun print();
}


/**
 * 类委托
 */
interface IEntrust {
    fun print()
}

class Entrust : IEntrust {
    override fun print() {
        Log.i("xiongliang", "打印 Entrust")
    }
}

class myEntrust(iEntrust: IEntrust) : IEntrust by iEntrust

/**
 * 属性委托
 */
class PropertyDelege {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "getValue thisRef=${thisRef} + property=${property.name}"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        Log.i("xiongliang", "setValue $thisRef property=$property value=$value")
    }
}

class PropertyClass {
    var name: String by PropertyDelege()

    //非空属性
    var age:Int by Delegates.notNull<Int>()

    var observableAge:Int by Delegates.observable(20){
        property, oldValue, newValue ->
        Log.i("xiongliang","可观测委托 property=$property oldValue=$oldValue newValue=$newValue")
    }

    /**
     * 中缀方法
     */
    infix fun infixFun(num:Int){
        Log.i("xiongliang","打印中缀方法 num $num")
    }
}

//延迟属性
val initAge:Int by lazy(LazyThreadSafetyMode.NONE) {
    Log.i("xiongliang","lazy  age")
    29
}

//map 委托, 只读通过map, 读写 通过mutableMap
class MapDelegate(map:Map<String,Any>){
   val name:String by map
   val age:Int by map
   val address:String by map
}


//lamda 表达式
fun lamdaExpress(a:Int, b:Int, compute: (x:Int,y:Int)->Unit){
    compute(a,b)
}

//可变参数, 可变参数只能呢个作为最后一个参数.
fun varargParam(vararg params:String){
    for (item in params){
        Log.i("xiongliang","打印item="+item)
    }
}

fun varargParam1(nums:Int = 9,vararg params:String){
    for (item in params){
        Log.i("xiongliang","打印item="+item)
    }
}
