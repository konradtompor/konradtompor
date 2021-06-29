package com.treelineinteractive.recruitmenttask.ui

import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.data.repository.RepositoryRequestStatus
import com.treelineinteractive.recruitmenttask.data.repository.ShopRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainViewModel.MainViewState, MainViewModel.MainViewAction>(MainViewState()) {

    data class MainViewState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val items: List<ProductItem> = listOf()
    ) : BaseViewState {
        val isSuccess: Boolean
            get() = !isLoading && error == null
    }

    sealed class MainViewAction : BaseAction {
        object LoadingProducts : MainViewAction()
        data class ProductsLoaded(val items: List<ProductItem>) : MainViewAction()
        data class ProductsLoadingError(val error: String) : MainViewAction()
    }

    private val shopRepository = ShopRepository()

    fun loadProducts() {
        GlobalScope.launch {
            shopRepository.getProducts()
                .collect { result ->
                    when (result.requestStatus) {
                        is RepositoryRequestStatus.FETCHING -> {
                            sendAction(MainViewAction.LoadingProducts)
                        }
                        is RepositoryRequestStatus.COMPLETE -> {
                            sendAction(MainViewAction.ProductsLoaded(result.data))
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
    }
}