package com.treelineinteractive.recruitmenttask.data.network.model

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("color") val color: String,
    @SerializedName("available") val available: Int,
    @SerializedName("cost") val cost: Float,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)