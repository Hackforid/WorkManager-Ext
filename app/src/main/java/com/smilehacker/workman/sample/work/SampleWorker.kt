package com.smilehacker.workman.sample.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by quan.zhou on 2021/4/13.
 */
class SampleWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        Thread.sleep(5000);
        Log.i("Sample", "do actual work at ${Thread.currentThread().name} in " + this.javaClass.name)
        Log.i("Sample", "I'm work")
        return Result.retry()
    }
}