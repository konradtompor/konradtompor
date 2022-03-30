package com.treelineinteractive.recruitmenttask.data.network.service

import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import retrofit2.http.GET

interface ShopService {
    @GET("/getInventory")
    suspend fun getInventory(): List<ProductItem>
}