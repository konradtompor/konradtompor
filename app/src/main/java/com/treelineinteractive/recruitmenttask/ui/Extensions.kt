package com.treelineinteractive.recruitmenttask.ui

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding

/** Activity binding delegate, may be used since onCreate up to onDestroy (inclusive) */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline factory: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        factory(layoutInflater)
    }

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun <T> LiveData<T>.observer(lifecycleOwner: LifecycleOwner, setter: (T) -> Unit) {
    observe(lifecycleOwner, { setter(it) })
}
