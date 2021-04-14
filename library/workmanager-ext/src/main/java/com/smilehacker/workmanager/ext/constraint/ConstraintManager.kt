package com.smilehacker.workmanager.ext.constraint

import com.smilehacker.workmanager.ext.ConstraintWorker
import java.util.*

/**
 * Created by quan.zhou on 2021/4/14.
 */
typealias ConstraintPair = Pair<Class<out ConstraintController<*>>, *>

object ConstraintManager {

    private val mConstraintControllers: MutableCollection<ConstraintController<*>> = HashSet()
    private val mWorkerConstraints: WeakHashMap<ConstraintWorker, List<ConstraintPair>> = WeakHashMap()

    fun <T> registerController(constraintController: ConstraintController<T>) {
        mConstraintControllers.add(constraintController)

        constraintController.addOnConstraintUpdateListener(object : OnConstraintUpdateListener<T> {

            override fun onConstraintUpdated(data: T) {
                synchronized(mWorkerConstraints) {
                    mWorkerConstraints.forEach { entry ->
                        if (entry.value.find { it.first == constraintController.javaClass } != null) {
                            if (isConstrained(entry.value)) {
                                entry.key.continueWork()
                            }
                        }
                    }
                }
            }

        })
    }

    fun registerWorker(worker: ConstraintWorker, constraints: List<ConstraintPair>) {
        synchronized(mWorkerConstraints) {
            mWorkerConstraints[worker] = constraints
        }
    }

    fun unregisterWorker(worker: ConstraintWorker) {
        synchronized(mWorkerConstraints) {
            mWorkerConstraints.remove(worker)
        }
    }

    fun isConstrained(constraints: List<ConstraintPair>): Boolean {
        constraints.forEach { pair ->
            val controller = mConstraintControllers.find { c -> c.javaClass ==  pair.first } ?: return false
            if (controller.getValue() != pair.second) {
                return false
            }
        }

        return true
    }


}