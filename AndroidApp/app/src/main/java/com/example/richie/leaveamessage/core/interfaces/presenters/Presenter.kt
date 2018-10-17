package com.example.richie.leaveamessage.core.interfaces.presenters

/**
 * Created by richard.macias on 10/16/18
 * Modernizing Medicine
 **/
interface Presenter<V>{
    fun attachView(view:V)
    fun detachView()
}