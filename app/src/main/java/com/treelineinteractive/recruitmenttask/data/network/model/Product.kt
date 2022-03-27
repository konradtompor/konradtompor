package com.treelineinteractive.recruitmenttask.data.network.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val type: String,
    val color: String,
    val available: Int,
    val cost: BigDecimal,
    val title: String,
    val description: String
)