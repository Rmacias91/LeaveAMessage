package com.example.richie.leaveamessage.core.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by richard.macias on 10/21/18
 * Modernizing Medicine
 **/
public class RxUtils {
    private RxUtils() {
        throw new IllegalStateException("RxJava Utils class!");
    }

    public static <T>ObservableTransformer<T, T> attachSchedulerWithObserver() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public static <T>ObservableTransformer<T, T> attachSchedulerWithOutObserver() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
