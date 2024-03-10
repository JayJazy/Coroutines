package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MainActivity : AppCompatActivity() {

    private var threadCount = 0
    private val lock = Any()

    private var coroutineCount = 0
    private val mutex = Mutex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val threadBtn = findViewById<Button>(R.id.threadBtn)
        val coroutineBtn = findViewById<Button>(R.id.coroutineBtn)

        val coroutine = CoroutineScope(Dispatchers.Default)

        // 스레드 실행
        threadBtn.setOnClickListener {
            startThread()
        }


        // 코루틴 실행
        coroutineBtn.setOnClickListener {
            coroutine.launch{
                startCoroutine()
            }
        }

    }


    private fun startThread() {
        val randomTimeMillis = (5000..10000).random().toLong()
        Thread {
            Thread.sleep(randomTimeMillis)
            // 하나의 스레드에서만 값이 증가 가능
            synchronized(lock) {
                threadCount += 1
                Log.d("TAG", "${threadCount}번 스레드(${randomTimeMillis}초)")
            }

        }.start()
    }


    private suspend fun startCoroutine()
    {
        val randomTimeMillis = (1000..5000).random().toLong()
        delay(randomTimeMillis)
        // 하나의 코루틴에서만 값이 증가 가능
        mutex.withLock {
            coroutineCount += 1
            Log.d("TAG", "${coroutineCount}번 코루틴(${randomTimeMillis}초)")
        }
    }

}