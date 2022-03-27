package com.treelineinteractive.recruitmenttask.ui.main

import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.ui.BaseAction

sealed class MainViewAction : BaseAction {
    object LoadingProducts : MainViewAction()
    data class ProductsLoaded(val items: List<ProductItem>) : MainViewAction()
    data class ProductsLoadingError(val error: String) : MainViewAction()
}