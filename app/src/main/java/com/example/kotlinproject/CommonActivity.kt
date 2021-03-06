package com.example.kotlinproject

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.concurrent.atomic.AtomicInteger
import kotlin.jvm.Throws
import kotlin.properties.Delegates
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.functions

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
//        method7()
//        method8()
//        method9()
        method10()
//        method11()
//        method12()
//        testThread()
    }

    /**
     * 测试线程
     */
    fun testThread() {
        AtomicInteger().incrementAndGet()
        var n = 0
        for (index in 0..3) {
            var thread = Thread(object : Runnable {
                override fun run() {
                    for (i in 0..1000) {
                        synchronized(CommonActivity::testThread){
                            n++
                            Log.i("xiongliang","打印Thread name="+Thread.currentThread().name +"n的值="+n)
                        }
                    }
                }

            })
            thread.start()
        }

        Handler().postDelayed(object:Runnable{
            override fun run() {
                Log.i("xiongliang","打印n="+n)
            }
        },1000*10)
    }

    /**
     * 遍历数组
     */
    fun method1() {
        var array = intArrayOf(1, 2, 4, 5) // 数组int[]
        var array1 = arrayOf(1, 2, 3, 4) //数组Interger[]
        var strings = arrayListOf<String>("hello", "world", "list", "test") //集合ArrayList<String>
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
    fun method7() {
        lamdaExpress(1, 2) { x, y ->
            Log.i("xiongliang", "执行lamda 表达式")
            Log.i("xiongliang", "打印x+y=" + x + y)
        }
        //可变参数
        varargParam("1", "2", "3")
        varargParam1(params = *arrayOf("1", "2"))

        var propertyClass = PropertyClass()
        //中缀方法调用
        propertyClass infixFun (9)

        //高阶函数
        var add: (Int, Int) -> Int = { a, b -> a + b }
        var subtract = { a: Int, b: Int -> a - b }

        Log.i("xiongliang", "高阶函数add=" + add(1, 2))
        Log.i("xiongliang", "高阶函数subtract=" + subtract(1, 2))

        var str = "abcd456fssdf"
        var result = str.filter {
            if (it >= 'a' && it <= 'z') {
                return@filter true
            }
            return@filter false
        }
        Log.i("xiongliang", "打印扩展String = " + result)

        var sum = ""
        var strings = arrayOf("hello", "world", "helloD", "welcome")
        strings.filter { it.contains("d", ignoreCase = true) }.map { it.toUpperCase() }.forEach {
            sum += it
            Log.i("xiongliang", "打印字符串=" + it)
        }
        //闭包使用
        Log.i("xiongliang", "打印闭包操作sum=" + sum)

        //函数字面值
        val numSubtract: Int.(other: Int) -> Int = { other ->
            this - other
        }
        Log.i("xiongliang", "打印字面值=" + 1.numSubtract(3))

        //解构声明
        var (name1, age1) = Person("xiongliang", 29)
        var (name2, age2) = Pair("xiongliang", 20)
        Log.i("xiongliang", "打印解构声明  name1= $name1 age1 $age1")
        Log.i("xiongliang", "打印解构声明 pair name2= $name2 age2 $age2")
        var map2 = mapOf("a" to "aa", "b" to "bb", "c" to "cc")
        map2.mapValues { (key, value) -> "$value hello" }.forEach {
            Log.i("xiongliang", "解构声明 调用map=" + it.value)
        }
    }

    /***
     * 测试注解
     */
    @MyAnnotation("hello", Int::class)
    fun method8() {
    }

    /**
     * 嵌套类和内部类
     * 依次调用: 嵌套类,内部类, 伴生对象,调用方式不一样,因为其内部的实现不一样.
     *
     */
    fun method9() {
        OutClass.NestClass().foo()
        OutClass().InnerClass().foo()
        Log.i("xiongliang", "打印伴生对象的变量值=" + Home1.instanceName)
        Log.i("xiongliang", "打印伴生对象的方法=" + Home1.Instance.getName1())
        Home1().age = "11"
    }

    /**
     * kotlin 与 java 互调
     */
    fun method10() {
        var list = ArrayList<String>()  //java 集合方式
        list.add("1")
        list.add("2")
        for (item in list) {
            Log.i("xiongliang", "打印item=" + item)
        }

//        var item:String = Utils.getText()
        Utils.getJavaInnerMethod()
//        Log.i("xiongliang","打印item="+item)

        var outClass = OutClass()


        var num1s = arrayOf(1, 2, 3)  //装箱数组 Integer[]
        var num2s = intArrayOf(1, 2, 3)  //原生数组int[]
//        var num3s:Array<Any> = num2s  //kotlin 不允许变化,java 允许

        Log.i("xiongliang", "打印class类型 ${EmptyClass("11")::class.java}")
        Log.i("xiongliang", "打印class类型 ${EmptyClass("11").javaClass}")
    }

    /**
     * 反射
     */
    fun method11() {
        Log.i("xiongliang", "获取KClasss实例" + String::class)
        Log.i(
            "xiongliang", "获取class " +
                    "" + String::class.java
        )
        Log.i("xiongliang", "------------------------------")
        Log.i("xiongliang", "获取对象实例的KClass" + String()::class)
        Log.i("xiongliang", "获取对象实例的Class" + String()::class.java)

        Log.i("xiongliang", "打印initAge=" + ::initAge.get())
        Log.i(
            "xiongliang",
            "打印 Student Age=" + Student::age + ",其值为=" + Student::age.get(Student("23", 1))
        )
        Log.i("xiongliang", "--------------------")
        Log.i("xiongliang", "打印构造方法引用=" + ::Student)
//        Log.i("xiongliang","打印Student 创建默认参数的对象="+Student::class.createInstance())
        //通过构造方法创建对象
        Student::class.constructors.forEach {
            if (it.parameters.size == 2) {
                var instance = it.call("xiongliang", 56)
                Log.i("xiongliang", "打印构造函数创建对象=" + instance.age)
            }
        }
        Log.i("xiongliang", "--------------------")
        Log.i(
            "xiongliang",
            "打印Student printUserNam.invoke=" + Student::printUserName.invoke(Student("56", 29))
        )
        Log.i(
            "xiongliang",
            "打印Student printUserAge.call=" + Student::printUserAge.call(Student("78", 29), 2)
        )
        Log.i("xiongliang", "--------------------")
        Log.i("xiongliang", "打印伴生对象=" + Home1::class.companionObject)


    }

    /**
     * 函数组合
     */
    fun method12() {
        val lists = listOf("adb", "ad", "sdff", "sdfff")
        val eventLength = combinaFun(::function1, ::function2)
        lists.filter(eventLength).forEach {
            Log.i("xiongliang", "打印item=" + it)
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

    fun printUserAge(age: Int) {
        Log.i("xiongliang", "打印 printUserAge= " + age)
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

fun String.filter(predicate: (Char) -> Boolean): String {

    var result: StringBuffer = StringBuffer()
    for (index in 0 until length) {  //不包括length
        if (predicate(get(index))) {
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

        fun getName1(): String {
            return "instanceName"
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
     * 可以实现多个接口,也可以不实现接口.
     * 可以直接访问外部变量, 不用定义为final
     *
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
     *
     * by lazy 延迟属性委托只能应用于常量val
     * lateinit 属性延迟初始化只能应用于变量var
     *
     */
    fun testEntrust() {
        var myEntrust = myEntrust(Entrust()).print()

        var propertyClass = PropertyClass()
        propertyClass.name = "xiongliang"
        Log.i("xiongliang", "属性委托=" + propertyClass.name)

        Log.i("xiongliang", "打印延迟属性委托=" + initAge)
        Log.i("xiongliang", "打印延迟属性委托=" + initAge)

        propertyClass.age = 29
        Log.i("xiongliang", "打印非空属性委托Property.age=" + propertyClass.age)

        propertyClass.observableAge = 50
        Log.i("xiongliang", "打印可观测属性委托 observableAge=" + propertyClass.observableAge)

        var mapDelegate = MapDelegate(
            mapOf(
                "name" to "xiongliang",
                "age" to 29,
                "address" to "xiaotao"
            )
        )
        Log.i(
            "xiongliang",
            "打印 name= ${mapDelegate.name} age= ${mapDelegate.age} address=${mapDelegate.address}"
        )
    }

}


data class Person(
    @JvmField
    var name: String,
    val age: Int
)

data class Person1(var name: String = "1", var age: Int)

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

    //嵌套类,类似java 静态内部类
    class NestClass {
        fun foo() {
            Log.i("xiongliang", "嵌套类=")
        }
    }

    //内部类, 类似java非静态内部类
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

    abstract fun print()
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
    //属性延迟初始化,只能是类属性,并且是对象.
    lateinit var country: String

    var name: String by PropertyDelege()

    //非空属性
    var age: Int by Delegates.notNull<Int>()

    var observableAge: Int by Delegates.observable(20) { property, oldValue, newValue ->
        Log.i("xiongliang", "可观测委托 property=$property oldValue=$oldValue newValue=$newValue")
    }

    /**
     * 中缀方法
     */
    infix fun infixFun(num: Int) {
        Log.i("xiongliang", "打印中缀方法 num $num")
    }
}

//延迟属性, 只能通过val 修饰
val initAge: Int by lazy(LazyThreadSafetyMode.NONE) {
    Log.i("xiongliang", "lazy  age")
    29
}

//map 委托, 只读通过map, 读写 通过mutableMap
class MapDelegate(map: Map<String, Any>) {
    val name: String by map
    val age: Int by map
    val address: String by map
}


//lamda 表达式
@JvmOverloads
fun lamdaExpress(a: Int = 9, b: Int, compute: (x: Int, y: Int) -> Unit) {
    compute(a, b)
}

//可变参数, 可变参数只能呢个作为最后一个参数.
fun varargParam(vararg params: String) {
    for (item in params) {
        Log.i("xiongliang", "打印item=" + item)
    }
}

fun varargParam1(nums: Int = 9, vararg params: String) {
    for (item in params) {
        Log.i("xiongliang", "打印item=" + item)
    }
}

//注解定义
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class MyAnnotation(val str: String, val argClass: KClass<*>)

@Throws(FileNotFoundException::class)
fun kotlinThrowException() {
    System.out.println("调用KotlinThrowException")
    throw FileNotFoundException()
}

//函数组合
fun <A, B, C> combinaFun(fun1: (A) -> B, fun2: (C) -> A): (C) -> B {
    return { x -> fun1(fun2(x)) }
}

fun function1(num: Int) = (0 == num % 2)

fun function2(str: String) = str.length

