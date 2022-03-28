package com.treelineinteractive.recruitmenttask.ui.main

import com.treelineinteractive.recruitmenttask.data.network.model.Product
import com.treelineinteractive.recruitmenttask.ui.BaseAction

sealed class MainViewAction : BaseAction {
    object LoadingProducts : MainViewAction()
    data class ProductsLoaded(val items: ArrayList<Product>) : MainViewAction()
    data class ProductsLoadingError(val error: String) : MainViewAction()
    data class IncrementSold(val item: Product) : MainViewAction()
    data class DecrementSold(val item: Product) : MainViewAction()
}