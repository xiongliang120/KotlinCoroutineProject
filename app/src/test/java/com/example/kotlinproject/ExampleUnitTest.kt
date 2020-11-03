package com.example.kotlinproject

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    fun addition_isCorrect() {

    }

    /**
     * 批量创建协程
     */
    @Test
    fun createCoroutine3() {
        runBlocking {
            for (i in 0..100){
                launch {
                    delay(1000)
                    assert(true){
                        "A"
                    }
                }
            }
//            Log.i("xiongliang","hello work")
        }
    }

}
