package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var threadCount = 0
    private var coroutineCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val threadBtn = findViewById<Button>(R.id.threadBtn)
        val coroutineBtn = findViewById<Button>(R.id.coroutineBtn)

        val coroutine = CoroutineScope(Dispatchers.Default)
        val randomTimeMillis = (1000..5000).random().toLong()

        // 스레드 실행
        threadBtn.setOnClickListener {
            startThread(randomTimeMillis).start()
        }


        // 코루틴 실행
        coroutineBtn.setOnClickListener {
            coroutine.launch(Dispatchers.Default) {
                startCoroutine(randomTimeMillis)
            }
        }



    }


    private fun startThread(randomTimeMillis: Long): Thread {
        val thread = Thread{
            Thread.sleep(randomTimeMillis)
            threadCount += 1
            Log.d("Thread", "${threadCount}번 스레드(${randomTimeMillis}초)")
        }
        return thread
    }


    private suspend fun startCoroutine(randomTimeMillis: Long)
    {
        delay(randomTimeMillis)
        coroutineCount += 1
        Log.d("TAG", "${coroutineCount}번 코루틴(${randomTimeMillis}초)")
    }

}