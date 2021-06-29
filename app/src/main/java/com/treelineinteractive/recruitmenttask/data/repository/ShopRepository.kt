package com.treelineinteractive.recruitmenttask.data.repository

import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.data.network.service.ServiceFactory
import com.treelineinteractive.recruitmenttask.data.network.service.ShopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.supervisorScope

class ShopRepository {
    private val shopService = ServiceFactory.createService<ShopService>()
    private val productsLocalCache = ProductsLocalCache()

    fun getProducts(): Flow<RepositoryResult<List<ProductItem>>> {
        return combine(fetchProducts().flowOn(Dispatchers.IO), productsLocalCache.getProducts()) { status, data ->
            RepositoryResult(data, status)
        }
    }

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
}

class ProductsLocalCache {
    private val savedProducts = MutableStateFlow<List<ProductItem>>(listOf())

    suspend fun saveProducts(products: List<ProductItem>) {
        savedProducts.emit(products)
    }

    fun getProducts(): Flow<List<ProductItem>> = savedProducts
}