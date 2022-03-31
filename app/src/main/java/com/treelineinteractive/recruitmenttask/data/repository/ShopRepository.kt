package com.treelineinteractive.recruitmenttask.data.repository

import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.data.network.model.Report
import com.treelineinteractive.recruitmenttask.data.network.service.ServiceFactory
import com.treelineinteractive.recruitmenttask.data.network.service.ShopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.supervisorScope

class ShopRepository {
    private val shopService = ServiceFactory.createService<ShopService>()
    private val productsLocalCache = ProductsLocalCache()

    fun getProducts(): Flow<RepositoryResult<List<ProductItem>>> {
        return combine(
            fetchProducts().flowOn(Dispatchers.IO),
            productsLocalCache.getProducts()
        ) { status, data ->
            RepositoryResult(data, status)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchProducts(): Flow<RepositoryRequestStatus> = channelFlow {
        send(RepositoryRequestStatus.FETCHING)

        supervisorScope {
            try {
                productsLocalCache.saveProducts(shopService.getInventory())
                send(RepositoryRequestStatus.COMPLETE)
            } catch (e: Exception) {
                e.printStackTrace()
                send(RepositoryRequestStatus.Error(e))
            }
        }
    }

    fun getSalesForReport(): List<Report> {
        return productsLocalCache.getSoldProducts()
    }

    fun collectDailySold(report: Report) {
        if (productsLocalCache.getSoldProducts().any { it.date == report.date }) {
            productsLocalCache.updateSoldProducts(report)
        } else {
            productsLocalCache.saveSoldReportDay(report)
        }
    }
}

class ProductsLocalCache {
    private val savedProducts = MutableStateFlow<List<ProductItem>>(listOf())
    private val savedSoldProducts = ArrayList<Report>(arrayListOf())

    suspend fun saveProducts(products: List<ProductItem>) {
        savedProducts.emit(products)
    }

    fun saveSoldReportDay(report: Report) {
        savedSoldProducts.add(report)
    }

    fun updateSoldProducts(report: Report) {
        savedSoldProducts.last().products = report.products.filter { it.sold > 0 }
    }

    fun getProducts(): Flow<List<ProductItem>> = savedProducts

    fun getSoldProducts(): List<Report> = savedSoldProducts
}