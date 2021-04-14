package com.smilehacker.workmanager.ext.constraint


/**
 * Created by quan.zhou on 2021/4/14.
 */
open abstract class ConstraintController<T>: OnConstraintUpdateListener<T> {

    private var mValue: T? = null
    private var mOnConstraintUpdateListeners: MutableSet<OnConstraintUpdateListener<T>> = LinkedHashSet()

    @Synchronized
    override fun onConstraintUpdated(data: T) {
        mValue = data

        synchronized(mOnConstraintUpdateListeners) {
            mOnConstraintUpdateListeners.forEach {
                it.onConstraintUpdated(data)
            }
        }
    }

    fun addOnConstraintUpdateListener(listener: OnConstraintUpdateListener<T>) {
        synchronized(mOnConstraintUpdateListeners) {
            mOnConstraintUpdateListeners.add(listener)
        }
    }

    fun getValue(): T? {
        return mValue
    }

}

interface OnConstraintUpdateListener<T> {
    fun onConstraintUpdated(data: T)
}