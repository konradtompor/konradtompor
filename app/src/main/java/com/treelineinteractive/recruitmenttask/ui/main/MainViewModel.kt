package com.treelineinteractive.recruitmenttask.ui.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treelineinteractive.recruitmenttask.data.network.model.Product
import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.data.network.model.Report
import com.treelineinteractive.recruitmenttask.data.repository.RepositoryRequestStatus
import com.treelineinteractive.recruitmenttask.data.repository.ShopRepository
import com.treelineinteractive.recruitmenttask.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : BaseViewModel<MainViewState, MainViewAction>(
    MainViewState()
) {
    val products = MutableLiveData<List<Product>>()

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun collectReport() {
        shopRepository.collectDailySold(
            Report(
                products = state.items,
                date = getCurrentDateAndTime()
            )
        )
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

    fun onReportButtonClicked(): Intent {
        collectReport()
        return sendReport()
    }

    private fun decorateReport(): String {
        val lastReport = shopRepository.getSalesForReport().last()
        var reportMessage = EMAIL_SUBJECT + NEW_LINE
        if (checkIfAnySold(lastReport)) {
            lastReport.products.forEach {
                if (it.sold > 0) {
                    reportMessage = reportMessage.plus(prepareProduct(it))
                }
            }
        } else {
            reportMessage = reportMessage.plus(NO_SALES)
        }
        return reportMessage
    }

    private fun checkIfAnySold(report: Report): Boolean = report.products.any { it.sold > 0 }

    private fun prepareProduct(product: Product): String {
        return TITLE + product.title + NEW_LINE +
                TYPE + product.type + NEW_LINE +
                COLOR + product.color + NEW_LINE +
                SOLD + product.sold + NEW_LINE +
                AVAILABLE + product.available + NEW_LINE + NEW_LINE
    }

    private fun sendReport(): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:$BOSS_EMAIL")
        intent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
        intent.putExtra(Intent.EXTRA_TEXT, decorateReport())
        return intent
    }

    private fun ArrayList<ProductItem>.mapToData(): ArrayList<Product> {
        return this.map {
            Product(
                id = it.id,
                type = it.type,
                title = it.title,
                description = it.description,
                color = it.color,
                available = it.available
            )
        } as ArrayList<Product>
    }

    companion object {
        const val BOSS_EMAIL = "bossman@bosscompany.com"
        var EMAIL_SUBJECT = "Sales report " + getCurrentDateAndTime()
        const val TITLE = "Title: "
        const val TYPE = "Type: "
        const val COLOR = "Color: "
        const val SOLD = "Sold: "
        const val AVAILABLE = "Available: "
        const val NEW_LINE = "\n"
        const val NO_SALES = "NO Sales"

        fun getCurrentDateAndTime(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.BASIC_ISO_DATE
            return current.format(formatter)
        }
    }
}