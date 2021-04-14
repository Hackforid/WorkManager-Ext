package com.smilehacker.workman.sample.work

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.smilehacker.workmanager.ext.constraint.ConstraintController


/**
 * Created by quan.zhou on 2021/4/14.
 */
class OnForegroundConstraintController : ConstraintController<Boolean>() {

    init {
        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(ApplicationObserver())
    }

    inner class ApplicationObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onForeground() {
            Log.i("Sample", "onStart")
            onConstraintUpdated(true)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onBackground() {
            Log.i("Sample", "onStop")
            onConstraintUpdated(false)
        }
    }
}