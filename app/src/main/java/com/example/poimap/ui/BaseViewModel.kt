package com.example.poimap.ui

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    @CallSuper
    override fun onCleared() = disposables.clear()

    fun Disposable.bindToLifecycle(): Disposable = apply {
        disposables.add(this)
    }
}