package com.smilehacker.workman.sample.work

import android.content.Context
import android.util.Log
import androidx.work.WorkerParameters
import com.smilehacker.workmanager.ext.ConstraintWorker
import com.smilehacker.workmanager.ext.constraint.ConstraintController

/**
 * Created by quan.zhou on 2021/4/14.
 */
class SampleConstraintWorker(appContext: Context, params: WorkerParameters) :
    ConstraintWorker(appContext, params) {

    override fun needConstraints(): List<Pair<Class<out ConstraintController<*>>, *>> {
        return listOf(OnForegroundConstraintController::class.java to false)
    }

    override suspend fun doActualWork(): Result {
        Log.i("Sample", "do actual work at ${Thread.currentThread().name} in " + this.javaClass.name)
        return Result.success()
    }
}