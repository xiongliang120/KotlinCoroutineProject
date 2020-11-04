package com.example.kotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        createCoroutine()
//        createCoroutineBlock()
//        createCoroutinePublicScope()
//        createCoroutine3()
//        cancelCoroutine()
//        cancelCoroutine2()
//        jobTimeOut()
//        suspendMethod1()
        createCoroutineDispatcher()
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
        var job = GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            Log.i("xiongliang", "content")
        }


        Log.i("xiongliang", "11")
    }

    /**
     * 通过runBlocking 创建协程
     */
    fun createCoroutineBlock() = runBlocking {
        delay(timeMillis = 1000L)
        Log.i("xiongliang", "111111")

        coroutineScope {
            Log.i("xiongliang","333333333")
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
    fun cancelCoroutine()= runBlocking {
        var job = launch {
            try {
                repeat(20){
                    Log.i("xiongliang","index="+it)
                    delay(500)
                }
            }finally {
                withContext(NonCancellable){
                    Log.i("xiongliang","打印finally 块")
                    delay(1000)
                    Log.i("xiongliang","在finally  delay后执行代码片段")
                }
            }
        }


        delay(1000)
        Log.i("xiongliang","111")
        job.cancelAndJoin()
    }

    /**
     * 协程取消,针对计算任务
     */
    fun cancelCoroutine2() = runBlocking {
       var job = launch(Dispatchers.Default) {
           var i =0
           var startTime = System.currentTimeMillis()
           while (isActive){
               if(System.currentTimeMillis() >= startTime){
                   Log.i("xiongliang","print i="+(i++))
                   startTime += 500L
               }
           }
       }
       delay(1000)
       job.cancelAndJoin()
       Log.i("xiongliang","打印协程是否被删除")
    }

    /**
     * 协程超时,
     * withTimeout(time), 会抛异常
     * withTimeOrNull(time),会返回null
     */
    fun  jobTimeOut() = runBlocking {
//        withTimeout(2000){
//            repeat(200){
//                Log.i("xiongliang","打印i="+it)
//                delay(400)
//            }
//        }

        var job = withTimeoutOrNull(2000){
            repeat(2){
                Log.i("xiongliang","打印i="+it)
                delay(400)
            }
        }

        Log.i("xiongliang","打印job="+job)

    }

    /**
     * 挂起函数的组合, async 和 await 实现并发
     *
     * 延迟执行,设置start参数we
     */
    fun suspendMethod1() = runBlocking {
        var expertTime = measureTimeMillis {
            var time1 =  async { initValue1() }
            var time2 = async{ initValue2() }

            var result1 = time1.await()
            var result2 = time2.await()

            Log.i("xiongliang","打印函数结果="+ (result1 + result2))
        }
        Log.i("xiongliang","打印函数的执行时间="+expertTime)

        var expertTime1 = measureTimeMillis {
            var time1 =  async(start = CoroutineStart.LAZY) { initValue1() }
            var time2 = async(start = CoroutineStart.LAZY){ initValue2() }

            Log.i("xiongliang","async 执行会堵塞外部协程执行11=")
            time1.start()
            time2.start()

            var result1 = time1.await()
            var result2 = time2.await()
            Log.i("xiongliang","async 执行会堵塞外部协程执行=")

            Log.i("xiongliang","打印函数结果="+ (result1 + result2))
        }
        Log.i("xiongliang","打印函数的执行时间="+expertTime1)

        Log.i("xiongliang","-----------------------------")

        var result1 = initValueAsync()
        runBlocking {
            Log.i("xiongliang","打印 result1 = "+ result1.await())
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
     * Unconfined -- 不会限制到特定线程上
     * 默认 -- 会继承启动它的那个上下文和分发器
     * default -- 会使用默认的分发器来启动协程,并使用后台的共享线程池来运行协程代码，等价于 GlobalScope.launch()
     * 线程池 -- 创建线程池,执行协程代码,并需要关闭协程分发器dispatcher.close().
     */
    fun createCoroutineDispatcher() = runBlocking{
        launch {
            Log.i("xiongliang","launch1 Thread name"+Thread.currentThread().name)
        }

        launch(Dispatchers.Unconfined) {
            Log.i("xiongliang","launch2 Thread name"+Thread.currentThread().name)
        }

        launch(Dispatchers.Default) {
            Log.i("xiongliang","launch3 Thread name"+Thread.currentThread().name)
        }

        GlobalScope.launch {
            Log.i("xiongliang","launch4 Thread name"+Thread.currentThread().name)
        }

        var singleThreadPoolDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        launch(singleThreadPoolDispatcher) {
            Log.i("xiongliang","launch5 Thread name"+Thread.currentThread().name)
            singleThreadPoolDispatcher.close()
        }

    }

    /**
     * 挂起函数
     */
    suspend fun initValue1():Int{
       delay(2000)
       return 20
    }

    suspend fun initValue2():Int{
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
