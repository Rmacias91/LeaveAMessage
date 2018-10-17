package com.example.richie.leaveamessage.core.utils

import com.example.richie.leaveamessage.core.interfaces.utils.RxUtils
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by richard.macias on 10/16/18
 * Modernizing Medicine
 **/
class RxUtilsImp:RxUtils {

    override fun <T : ObservableTransformer<T, T>> applySchedulerObserver(): T {
        return { observer:Observable<T>  -> //FU*K I need help.
            observer
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
        }
    }

    override fun <T : ObservableTransformer<T, T>> applySchedulerNoObserver(): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}