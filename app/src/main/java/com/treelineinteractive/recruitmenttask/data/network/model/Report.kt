package com.treelineinteractive.recruitmenttask.data.network.model

data class Report(
    var products: List<Product> = arrayListOf(),
    val date: String
)