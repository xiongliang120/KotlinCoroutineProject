package com.example.kotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.view.postDelayed
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

/***
 * Kotlin 协程的使用
 *
 *
 * 参考: https://blog.csdn.net/weixin_34191845/article/details/91457625
 */

class MainActivity : AppCompatActivity() {
    private var nameButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
//        createCoroutine()
//        createCoroutineBlock()
//        createCoroutinePublicScope()
//        createCoroutine3()
//        cancelCoroutine()
//        cancelCoroutine2()
//        jobTimeOut()
//        suspendMethod1()
//        suspendMethod2()
//        createCoroutineDispatcher()
//        createCoroutineTest()
//        createParentChildCoroutineScope()
//        changeThreadLocal()
//        testCoroutineJob()
        createFlow()
//        cancelFlow()
//        flowOperator()
//        loopCallMethod()
//        channelMessage()
//        flowCombina()
//        flowCompeletion()
    }

    fun initView() {
        nameButton = findViewById(R.id.name)
    }


    /***
     * 创建协程
     *  GlobalScope.launch() 是无阻塞的,不会阻塞UI 线程
     *  Job.cancel() -- 取消协程
     *  Job.join()  -- 让协程运行完
     *
     *  Dispatchers 协程调度
     *  Default -- 共享后台线程池里的线程
     *  Main -- Android 主线程
     *  IO  -- 共享后台线程池里的线程
     *  newSingleThreadContext -- 使用新的线程
     *
     */
    fun createCoroutine() {
        var job = GlobalScope.launch {
            Log.i("xiongliang", " Thread name11 " + Thread.currentThread().name)
            delay(3000)
            Log.i("xiongliang", " Thread name22 " + Thread.currentThread().name)
//            launch(Dispatchers.Main) {
//                Log.i("xiongliang", " Thread name33 " + Thread.currentThread().name)
//            }

            withContext(Dispatchers.Main){
                delay(1000)
                Log.i("xiongliang", " Thread test33 " + Thread.currentThread().name)
            }

            Log.i("xiongliang", "Thread name44 " + Thread.currentThread().name)
        }
        Log.i("xiongliang", "Thread name55 " + Thread.currentThread().name)
    }

    /**
     * coroutineScope 创建协程作用域, 协程等待该协程作用域返回
     */
    fun createCoroutineBlock() = runBlocking {
        Log.i("xiongliang", "00000")
        delay(timeMillis = 1000L)
        Log.i("xiongliang", "111111 Thread name="+Thread.currentThread().name)


        coroutineScope {
            Log.i("xiongliang", "333333333 Thread name="+Thread.currentThread().name)
            delay(2000)
        }
        Log.i("xiongliang", "22222222")
    }


    /**
     * 外部协程会等待起作用域内启动的协程执行完毕后才会执行完成
     */
    fun createCoroutinePublicScope() =
        GlobalScope.launch {
            Log.i("xiongliang", "111111")
            GlobalScope.launch {
                delay(2000)
                Log.i("xiongliang", "222222222222")
            }
            Log.i("xiongliang", "333333333")
        }

    /**
     * 批量创建协程
     */
    fun createCoroutine3() = runBlocking {
        repeat(10) {
            launch {
                delay(1000)
                Log.i("xiongliang", "A")
            }
        }


        Log.i("xiongliang", "hello work")
    }


    /**
     * 协程取消和超时,清理操作放在finally 中
     */
    fun cancelCoroutine() = runBlocking {
        var job = launch() {
            try {
                repeat(20) {
                    Log.i("xiongliang", "index=" + it)
                    delay(500)
                }
            } finally {
                withContext(NonCancellable) {
                    Log.i("xiongliang", "打印finally 块")
                    delay(1000)
                    Log.i("xiongliang", "在finally  delay后执行代码片段")
                }
            }
        }


        delay(1000)
        Log.i("xiongliang", "111")
        job.cancelAndJoin()
    }

    /**
     * 协程取消,针对计算任务
     */
    fun cancelCoroutine2() = runBlocking {
        Log.i("xiongliang","Thread="+Thread.currentThread().name)
        var job = launch(Dispatchers.Default) {
            var i = 0
            var startTime = System.currentTimeMillis()
            while (isActive) {
                if (System.currentTimeMillis() >= startTime) {
                    Log.i("xiongliang", "print i=" + (i++))
                    startTime += 500L
                }
            }
        }
        delay(2000)
        job.cancelAndJoin()
        Log.i("xiongliang", "打印协程是否被删除")
    }

    /**
     * 协程超时,
     * withTimeout(time), 会抛异常
     * withTimeOrNull(time),会返回null
     */
    fun jobTimeOut() = runBlocking {
//        var job = withTimeout(2000){
//            repeat(5){
//               Log.i("xiongliang","打印withTimeout i"+it)
//               delay(400)
//            }
//        }

//        Log.i("xiongliang","打印job="+job)

        var job1 = withTimeoutOrNull(2000) {
            repeat(2) {
                Log.i("xiongliang", "打印withTimeoutNull i=" + it)
                delay(400)
            }
        }

        Log.i("xiongliang", "打印job1=" + job1)

    }

    /**
     * 挂起函数,常规使用
     */
    fun suspendMethod1() = runBlocking(Dispatchers.Default) {
        var expertTime = measureTimeMillis {
            Log.i("xiongliang", "111111  Thread name =" + Thread.currentThread().name)
            var result1 = initValue1()
            Log.i("xiongliang", "22222 Thread name =" + Thread.currentThread().name)
            var result2 = initValue2()
            Log.i("xiongliang", "打印函数结果=" + (result1 + result2))
        }


        Log.i("xiongliang", "打印函数执行时间=" + expertTime)
    }

    /**
     * 挂起函数的组合, async 和 await 实现并发
     *
     * 延迟执行,设置start参数we
     */
    fun suspendMethod2() = runBlocking {
        var expertTime = measureTimeMillis {
            var time1 = async { initValue1() }
            var time2 = async { initValue2() }

            Log.i("xiongliang","00000")
            var result1 = time1.await()
            Log.i("xiongliang","1111111")
            var result2 = time2.await()
            Log.i("xiongliang","2222222")

            Log.i("xiongliang", "打印函数结果=" + (result1 + result2))
        }
        Log.i("xiongliang", "打印函数的执行时间=" + expertTime)

        Log.i("xiongliang", "-----------------------------")

        var expertTime1 = measureTimeMillis {
            var time1 = async(start = CoroutineStart.LAZY) { initValue1() }
            var time2 = async(start = CoroutineStart.LAZY) { initValue2() }

            Log.i("xiongliang", "async 执行会堵塞外部协程执行11=")
            time1.start()
            time2.start()

            var result1 = time1.await()
            var result2 = time2.await()
            Log.i("xiongliang", "async 执行会堵塞外部协程执行=")

            Log.i("xiongliang", "打印函数结果=" + (result1 + result2))
        }
        Log.i("xiongliang", "打印函数的执行时间=" + expertTime1)

        Log.i("xiongliang", "-----------------------------")

        var result1 = initValueAsync()
        runBlocking {
            Log.i("xiongliang", "打印 result1 = " + result1.await())
        }
    }


    /**
     * 定义异步风格函数
     */
    fun initValueAsync() = GlobalScope.async {
        initValue1()
    }

    /**
     * 使用协程分发器 指定运行的线程
     * Unconfined -- 调用者的线程去启动协程，但只会持续到第一个挂起点,恢复后由挂起函数所在的线程执行,
     * 适用场景即不会限制到特定线程上。
     * 默认 -- 会继承启动它的那个上下文和分发器
     * default -- 会使用默认的分发器来启动协程,并使用后台的共享线程池来运行协程代码，等价于 GlobalScope.launch()
     * 线程池 -- 创建线程池,执行协程代码,并需要关闭协程分发器dispatcher.close().
     */
    fun createCoroutineDispatcher() = runBlocking {
        Log.i("xiongliang","打印 runBlocking的Thread name="+Thread.currentThread().name)
        launch {
            Log.i("xiongliang", "缺省 Thread name " + Thread.currentThread().name)
        }

        launch(Dispatchers.Unconfined) {
            newSingleThreadContext("测试Unconfined").use { thread1 ->
//                delay(1000)
                withContext(thread1) {
                    var i = 100
                    i++
                    Log.i("xiongliang", "Unconfined22 Thread name " + Thread.currentThread().name)
                }
                Log.i("xiongliang", "Unconfined33 Thread name " + Thread.currentThread().name)
            }
        }

        launch(Dispatchers.Default) {
            Log.i("xiongliang", "Default Thread name " + Thread.currentThread().name)
        }

        GlobalScope.launch {
            Log.i("xiongliang", "全局缺省 Thread name " + Thread.currentThread().name)
        }

        var singleThreadPoolDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        launch(singleThreadPoolDispatcher) {
            Log.i("xiongliang", "线程池 Thread name  " + Thread.currentThread().name)
            singleThreadPoolDispatcher.close()
        }

    }

    /**
     * 协程调试,打印协程名字
     * 通过JVM 配置 -Dkotlinx.coroutines.debug
     *
     * newSingleThreadContext,会创建新的线程执行协程代码(协程必须指定其为上下文), use 是线程不在使用时会被释放掉
     *
     */
    fun createCoroutineTest() {
        newSingleThreadContext("context1").use { user1 ->
            Log.i("xiongliang", "aaa Thread name=" + Thread.currentThread().name)
            newSingleThreadContext("context2").use { user2 ->
                Log.i("xiongliang", "000 Thread name=" + Thread.currentThread().name)
                runBlocking(user1) {
                    var job = coroutineContext[Job]
                    Log.i(
                        "xiongliang",
                        "111 Thread name=" + Thread.currentThread().name + ".." + job
                    )
                    withContext(user2) {
                        var job = coroutineContext[Job]
                        Log.i(
                            "xiongliang",
                            "222 Thread name=" + Thread.currentThread().name + ".." + job
                        )
                        delay(2000)
                    }
                    Log.i("xiongliang", "333 Thread name=" + Thread.currentThread().name)
                }
            }
        }
    }

    /**
     * 父子协程,父协程被取消时, 其所有子协程会递归取消
     */
    fun createParentChildCoroutineScope() = runBlocking {
        var job1 = launch(CoroutineName("自定义协程名字") + Dispatchers.Default) {
            Log.i("xiongliang", "111111")
            var job2 = launch {
                Log.i("xiongliang", "job2  2222222")
                delay(4000)
                Log.i("xiongliang", "job2  3333333")
            }

            var job3 = launch {
                Log.i("xiongliang", "job3  4444444")
                delay(2000)
                Log.i("xiongliang", "job3  5555555")
            }
        }
        delay(1000)
        job1.cancel()
    }

    /***
     * 协程在各个线程间切换ThreadLocal
     */

    val threadLocal = ThreadLocal<String>()
    fun changeThreadLocal() {
        GlobalScope.launch {
            threadLocal.set("helllo")
            Log.i("xiongliang", "1111.." + Thread.currentThread().name + "..." + threadLocal.get())
            var job = launch(Dispatchers.Default+ threadLocal.asContextElement("world")) {
                Log.i(
                    "xiongliang",
                    "22222.." + Thread.currentThread().name + "..." + threadLocal.get()
                )
                withContext(Dispatchers.Main){
                    Log.i(
                        "xiongliang",
                        "aaaaa.." + Thread.currentThread().name + "..." + threadLocal.get()
                    )

                    initValue1()
                }


                Log.i(
                    "xiongliang",
                    "33333.." + Thread.currentThread().name + "..." + threadLocal.get()
                )
                delay(2000)
            }
            Log.i("xiongliang", "44444.." + Thread.currentThread().name + "..." + threadLocal.get())
            job.join()
            Log.i("xiongliang", "5555555.." + Thread.currentThread().name + "..." + threadLocal.get())
        }

        Log.i("xiongliang", "66666666" + Thread.currentThread().name + "..." + threadLocal.get())
    }


    /**
     * 测试协程 Job的使用
     * job, 一个协程会等待另一个协程执行完毕后再执行
     */
    fun testCoroutineJob() = runBlocking {
          var job = launch{
              delay(2000)
              Log.i("xiongliang","welcome")
          }

          Log.i("xiongliang","Hello")
          job.join()
          Log.i("xiongliang","workld")
    }


    /***
     * 创建Flow
     */
    fun createFlow() = runBlocking {
//        flowMethod().collect {
//            Log.i("xiongliang","打印i="+it)
//        }

        flowMethod1().collect {
            Log.i("xiongliang", "打印i=" + it + "Thread name=" + Thread.currentThread().name)
        }

        flowMethod1().buffer().collect {
            Log.i("xiongliang", "buffer 使用打印i=" + it + "Thread name=" + Thread.currentThread().name)
        }
    }


    /**
     * Flow的取消
     */
    fun cancelFlow() = runBlocking {
        withTimeoutOrNull(400) {
            flowMethod().collect {
                Log.i("xiongliang", "打印i=" + it)
            }
        }

        Log.i("xiongliang", "hello")
    }

    /**
     * flow的操作符使用
     */
    fun flowOperator() = runBlocking {
        (1..10).asFlow().filter { it > 5 }.map { input -> intToString(input) }.collect {
            Log.i("xiongliang", "打印flow的操作符=" + it)
        }


        (1..10).asFlow().transform { input ->
            emit("hello")
            emit(intToString(2))
            emit("world")
        }.collect {
            Log.i("xiongliang", "打印transform=" + it)
        }
    }

    /**
     * 流的组合
     */
    fun flowCombina() = runBlocking {
        var nums = (1..5).asFlow()
        var string = flowOf("one", "two", "three", "four", "five")
        nums.zip(string){a,b->
             "$a"+"->"+"$b"
        }.collect {
            Log.i("xiongliang","打印it="+it)
        }
    }

    /**
     * 捕获异常
     * catch, onCompletion 均可捕获异常, 不过只能捕获上游异常
     */
    fun flowCompeletion() = runBlocking {
        flowMethod1().onCompletion {
            Log.i("xiongliang","flow  完成")
        }.catch {cause ->
            if(cause != null){
                Log.i("xiongliang","flow 发生异常")
            }
        }.collect {
            Log.i("xiongliang", "打印i=" + it + "Thread name=" + Thread.currentThread().name)
        }
    }


    fun intToString(i: Int): String {
        return "output" + i
    }

    private fun flowMethod(): Flow<Int> = flow {
        for (i in 1..4) {
            delay(200)
            emit(i)
        }
    }

    private fun flowMethod1(): Flow<Int> = flow {
        for (i in 1..4) {
            delay(200)
            Log.i("xiongliang", "flowMethod1 Thread name=" + Thread.currentThread().name)
            emit(i)
        }
    }.flowOn(Dispatchers.Default)


    /**
     * 循环递归的调用挂起函数
     */
    fun loopCallMethod() {
        GlobalScope.launch {
            Log.i("xiongliang","0000000")
            while (true) {
                callMethod1()
            }
            Log.i("xiongliang","111111111")
        }
        Log.i("xiongliang","2222222")
    }


    suspend fun callMethod1() {
        Log.i("xiongliang", "循环递归调用挂起函数1111")
        delay(400)
        callMethod1()
    }

    /**
     * 协程间使用channel 通信
     */
    fun channelMessage() = runBlocking {
        val channel = Channel<Int>()
        //发送消息
        launch {
            for (i in 1..10) {
                Log.i("xiongliang", "发送数据")
                channel.send(i)
            }
            channel.close()
        }
        //接收消息
        launch {
            for (value in channel)
                Log.i("xiongliang", "打印接收到数据=" + value)
        }
    }

    /**
     * 挂起函数
     */
    suspend  fun initValue1(): Int {
        delay(2000)
        Log.i(
            "xiongliang",
            "打印挂起函数中的线程值.." + Thread.currentThread().name + "..." + threadLocal.get()
        )
        return 20
    }

    suspend fun initValue2(): Int {
        delay(3000)
        return 10
    }

    /**
     * suspend 关键字 -- 能够是协程执行暂停,等执行完毕后再返回结果,同时不会阻塞线程
     */
    suspend fun fetchData(): String {
        delay(200)
        return "content"
    }

}
