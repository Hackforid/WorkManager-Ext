package com.smilehacker.workman.sample.work

import android.content.Context
import android.util.Log
import androidx.work.WorkerParameters
import com.smilehacker.workmanager.ext.ConstraintWorker
import com.smilehacker.workmanager.ext.constraint.ConstraintController
import com.smilehacker.workmanager.ext.constraint.ConstraintMap
import kotlinx.coroutines.delay

/**
 * Created by quan.zhou on 2021/4/14.
 */
class SampleWaitConstraintWorker(appContext: Context, params: WorkerParameters) :
    ConstraintWorker(appContext, params) {

    override fun needConstraints(): ConstraintMap {
        return mapOf(OnForegroundConstraintController::class.java to false)
    }

    override suspend fun doActualWork(): Result {
        delay(2000)
        Log.i("Sample", "do actual work at ${Thread.currentThread().name} in " + this.javaClass.name)
        return Result.success()
    }
}