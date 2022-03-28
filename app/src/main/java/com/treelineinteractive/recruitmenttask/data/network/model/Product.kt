package com.treelineinteractive.recruitmenttask.data.network.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val type: String,
    val color: String,
    var available: Int,
    var sold: Int = 0,
    val cost: BigDecimal,
    val title: String,
    val description: String
)