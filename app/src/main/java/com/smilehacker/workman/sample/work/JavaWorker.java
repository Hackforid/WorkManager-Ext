package com.smilehacker.workman.sample.work;

import android.content.Context;
import android.util.Log;

import androidx.work.WorkerParameters;

import com.smilehacker.workmanager.ext.ConstraintWorker;
import com.smilehacker.workmanager.ext.constraint.ConstraintController;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import kotlin.coroutines.Continuation;

/**
 * Created by quan.zhou on 2021/4/14.
 */
public class JavaWorker extends ConstraintWorker {

    public JavaWorker(@NotNull Context appContext, @NotNull WorkerParameters params) {
        super(appContext, params);
    }

    @NotNull
    @Override
    public Map<Class<? extends ConstraintController<?>>, ?> needConstraints() {
        return new HashMap<Class<? extends ConstraintController<?>>, Object>() {{ put(OnForegroundConstraintController.class, false); }};
    }


    @Nullable
    @Override
    public Object doActualWork(@NotNull Continuation<? super Result> $completion) {
        Log.i("Sample", "Do java work at " + Thread.currentThread().getName());
        return Result.success();
    }
}
