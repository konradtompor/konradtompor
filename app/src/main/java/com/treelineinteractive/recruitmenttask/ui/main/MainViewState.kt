package com.treelineinteractive.recruitmenttask.ui.main

import com.treelineinteractive.recruitmenttask.data.network.model.Product
import com.treelineinteractive.recruitmenttask.ui.BaseViewState

data class MainViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: ArrayList<Product> = arrayListOf(),
) : BaseViewState {
    val isSuccess: Boolean
        get() = !isLoading && error == null

    fun incrementSold(product: Product): MainViewState {
        val itemsCopy = items
        val index = itemsCopy.indexOf(product)
        val item = itemsCopy.find { it.id == product.id }
        item?.let {
            itemsCopy[index] = item
        }
        return this.copy(
            items = itemsCopy
        )
    }

    fun decrementSold(product: Product): MainViewState {
        val itemsCopy = items
        val index = itemsCopy.indexOf(product)
        val item = itemsCopy.find { it.id == product.id }
        item?.let {
            itemsCopy[index] = item
        }
        return this.copy(
            items = itemsCopy
        )
    }
}