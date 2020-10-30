package com.example.kotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.concurrent.thread

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
        createCoroutineBlock()
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
    fun createCoroutine(){
        var job = GlobalScope.launch(Dispatchers.Main) {
            Log.i("xiongliang","content")
        }

        Log.i("xiongliang","11")
    }

    /**
     * 通过runBlocking 创建协程
     */
    fun createCoroutineBlock(){
        runBlocking {
              delay(timeMillis = 1000L)
             Log.i("xiongliang","111111")
        }
        Log.i("xiongliang","22222222")
    }



    /**
     * suspend 关键字 -- 能够是协程执行暂停,等执行完毕后再返回结果,同时不会阻塞线程
     */
    suspend fun fetchData():String{
        delay(200)
        return "content"
    }
}
