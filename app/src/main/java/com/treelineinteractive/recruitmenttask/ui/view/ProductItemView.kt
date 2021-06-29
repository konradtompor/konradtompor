package com.treelineinteractive.recruitmenttask.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.R
import com.google.android.material.card.MaterialCardView
import com.treelineinteractive.recruitmenttask.data.network.model.ProductItem
import com.treelineinteractive.recruitmenttask.databinding.ViewProductItemBinding

class ProductItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding  = ViewProductItemBinding.inflate(LayoutInflater.from(context), this)

    fun setProductItem(productItem: ProductItem) {
        binding.nameLabel.text = productItem.title
        binding.descriptionLabel.text = productItem.description
    }
}