package com.treelineinteractive.recruitmenttask.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseAction>(initialState: ViewState): ViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData = stateMutableLiveData.asLiveData()

    protected var state by Delegates.observable(initialState) { _, old, new ->
        if (old != new) {
            stateMutableLiveData.postValue(new)
        }
    }

    fun sendAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    protected open fun onReduceState(viewAction: ViewAction): ViewState = state
}

interface BaseViewState
interface BaseAction
