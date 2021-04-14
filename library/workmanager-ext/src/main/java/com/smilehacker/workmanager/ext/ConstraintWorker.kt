package com.smilehacker.workmanager.ext

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smilehacker.workmanager.ext.constraint.ConstraintManager
import com.smilehacker.workmanager.ext.constraint.ConstraintPair
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Created by quan.zhou on 2021/4/14.
 */
abstract class ConstraintWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val mLock = Mutex(true)

    final override suspend fun doWork(): Result {
        val constrains = needConstraints()

        Log.i("Sample", "start work at ${Thread.currentThread().name}")
        if (ConstraintManager.isConstrained(constrains)) {
            Log.i("Sample", "is constrained, do work.")
            return doActualWork()
        }


        Log.i("Sample", "register worker")
        ConstraintManager.registerWorker(this@ConstraintWorker, constrains)

        mLock.withLock {
        }

        Log.i("Sample", "condition unlocked at ${Thread.currentThread().name}")
        return doActualWork()
    }

    abstract fun needConstraints(): List<ConstraintPair>

    abstract suspend fun doActualWork(): Result

    fun continueWork() {
        mLock.unlock()
    }
}