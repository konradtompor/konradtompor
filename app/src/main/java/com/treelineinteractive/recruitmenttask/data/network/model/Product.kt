package com.treelineinteractive.recruitmenttask.data.network.model

data class Product(
    val id: String,
    val type: String,
    val color: String,
    var available: Int,
    var sold: Int = 0,
    val title: String,
    val description: String
)