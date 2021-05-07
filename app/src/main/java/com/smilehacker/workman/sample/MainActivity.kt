package com.smilehacker.workman.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.smilehacker.workman.sample.work.*
import com.smilehacker.workmanager.ext.constraint.ConstraintManager
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWorker()
        val btnTest = findViewById<Button>(R.id.btn_test)
        btnTest.setOnClickListener {
            test()
        }
    }

    private fun test() {
        val request = OneTimeWorkRequestBuilder<SampleConstraintWorker>()
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(SampleConstraintWorker::class.java.name, ExistingWorkPolicy.REPLACE, request)

//        val request1 = OneTimeWorkRequestBuilder<SampleWorker>()
//            .build()
//
//        WorkManager.getInstance(this)
//            .enqueue(request1)
//
//        val request2 = OneTimeWorkRequestBuilder<JavaWorker>()
//            .build()
//
//        WorkManager.getInstance(this)
//            .enqueue(request2)
//
//
//        WorkManager.getInstance(this)
//            .enqueue(OneTimeWorkRequestBuilder<SampleWaitConstraintWorker>().build())
    }

    private fun initWorker() {
        ConstraintManager.registerController(OnForegroundConstraintController())
    }

    private fun testGC() {
        val map = WeakHashMap<Collection<*>, String>()
        var key: Collection<Any>? = HashSet<Any>()
        map.put(key!!, "bbb")
        key = null

        System.gc()
        Log.i("Test", "map size = " + map.size)
        Log.i("Test", "map size = " + map.size)

        map.forEach { entry ->
            this.window.decorView.postDelayed({
                System.gc()
                Log.i("Test", "map size = " + map.size)
                Log.i("Test", "entry is " + entry.key)
            }, 5000)
        }


    }
}