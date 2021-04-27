package com.smilehacker.workmanager.ext

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smilehacker.workmanager.ext.constraint.ConstraintManager
import com.smilehacker.workmanager.ext.constraint.ConstraintMap
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

        if (ConstraintManager.isConstrained(constrains)) {
            return doActualWork()
        }


        ConstraintManager.registerWorker(this@ConstraintWorker, constrains)
        if (!mLock.isLocked) {
            mLock.lock()
        }

        mLock.withLock {
        }

        return doActualWork()
    }

    abstract fun needConstraints(): ConstraintMap

    abstract suspend fun doActualWork(): Result

    fun continueWork() {
        if (mLock.isLocked) {
            try {
                mLock.unlock()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}