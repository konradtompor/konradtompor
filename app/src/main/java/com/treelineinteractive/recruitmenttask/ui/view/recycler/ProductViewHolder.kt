package com.treelineinteractive.recruitmenttask.ui.view.recycler

import androidx.recyclerview.widget.RecyclerView
import com.treelineinteractive.recruitmenttask.data.network.model.Product
import com.treelineinteractive.recruitmenttask.databinding.ViewProductItemBinding

class ProductViewHolder(
    private val binding: ViewProductItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.run {
            nameLabel.text = product.title
            descriptionLabel.text = product.description
        }
    }
}