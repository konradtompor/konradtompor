package com.treelineinteractive.recruitmenttask.ui.main

import androidx.lifecycle.MutableLiveData
import com.treelineinteractive.recruitmenttask.data.network.model.Product
import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.data.repository.RepositoryRequestStatus
import com.treelineinteractive.recruitmenttask.data.repository.ShopRepository
import com.treelineinteractive.recruitmenttask.ui.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainViewState, MainViewAction>(
    MainViewState()
) {
    private val shopRepository = ShopRepository()

    val products = MutableLiveData<List<Product>>()

    fun loadProducts() {
        GlobalScope.launch {
            shopRepository.getProducts()
                .collect { result ->
                    when (result.requestStatus) {
                        is RepositoryRequestStatus.FETCHING -> {
                            sendAction(MainViewAction.LoadingProducts)
                        }
                        is RepositoryRequestStatus.COMPLETE -> {
                            val productsData = (result.data as ArrayList).mapToData()
                            sendAction(MainViewAction.ProductsLoaded(productsData))
                            products.postValue(productsData)
                        }
                        is RepositoryRequestStatus.Error -> {
                            sendAction(MainViewAction.ProductsLoadingError("Oops, something went wrong"))
                        }
                    }
                }
        }
    }

    override fun onReduceState(viewAction: MainViewAction): MainViewState = when (viewAction) {
        is MainViewAction.LoadingProducts -> state.copy(isLoading = true, error = null)
        is MainViewAction.ProductsLoaded -> state.copy(
            isLoading = false,
            error = null,
            items = viewAction.items
        )
        is MainViewAction.ProductsLoadingError -> state.copy(
            isLoading = false,
            error = viewAction.error
        )
        is MainViewAction.IncrementSold -> {
            state.incrementSold(viewAction.item)
        }
        is MainViewAction.DecrementSold -> {
            state.decrementSold(viewAction.item)
        }
    }

    fun onIncButtonClicked(product: Product) {
        sendAction(MainViewAction.IncrementSold(product))
    }

    fun onDecButtonClicked(product: Product) {
        sendAction(MainViewAction.DecrementSold(product))
    }

    private fun ArrayList<ProductItem>.mapToData(): ArrayList<Product> {
        return this.map {
            Product(
                id = it.id,
                type = it.type,
                title = it.title,
                description = it.description,
                color = it.color,
                available = it.available,
                cost = it.cost.toBigDecimal()
            )
        } as ArrayList<Product>
    }
}