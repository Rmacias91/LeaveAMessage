package com.example.richie.leaveamessage.core.interfaces.presenters

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by richard.macias on 10/16/18
 * Modernizing Medicine
 **/
abstract class BasePresenter<V>:Presenter<V>{
    private var disposables:CompositeDisposable? = CompositeDisposable()

    //Kotlin parameter values are final by default and can't be changed.
    protected fun addSubscription(compositeDisposable: CompositeDisposable){
        val disposable = disposables
        if(disposable == null || disposable.isDisposed){
            disposables = CompositeDisposable()
        }
        disposables?.add(compositeDisposable)
    }

    protected fun unSubscribeAll(){
        val disposable = disposables
        if(disposable != null && !disposable.isDisposed){
            disposables?.dispose()
            disposables = null
        }
    }
}