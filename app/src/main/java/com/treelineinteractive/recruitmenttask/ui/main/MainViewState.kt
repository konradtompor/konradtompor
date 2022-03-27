package com.treelineinteractive.recruitmenttask.ui.main

import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.ui.BaseViewState

data class MainViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<ProductItem> = listOf()
) : BaseViewState {
    val isSuccess: Boolean
        get() = !isLoading && error == null
}