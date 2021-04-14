package com.smilehacker.workman.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.smilehacker.workman.sample.work.JavaWorker
import com.smilehacker.workman.sample.work.OnForegroundConstraintController
import com.smilehacker.workman.sample.work.SampleConstraintWorker
import com.smilehacker.workman.sample.work.SampleWorker
import com.smilehacker.workmanager.ext.constraint.ConstraintManager

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
            .enqueue(request)

        val request1 = OneTimeWorkRequestBuilder<SampleWorker>()
            .build()

        WorkManager.getInstance(this)
            .enqueue(request1)

        val request2 = OneTimeWorkRequestBuilder<JavaWorker>()
            .build()

        WorkManager.getInstance(this)
            .enqueue(request2)
    }

    private fun initWorker() {
        ConstraintManager.registerController(OnForegroundConstraintController())
    }
}