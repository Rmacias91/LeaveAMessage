package com.example.richie.leaveamessage.core.interfaces.utils

import io.reactivex.ObservableTransformer

/**
 * Created by richard.macias on 10/16/18
 * Modernizing Medicine
 **/
interface RxUtils {
    fun <T:ObservableTransformer<T,T>>applySchedulerObserver():T
    fun <T:ObservableTransformer<T,T>>applySchedulerNoObserver():T
}