package com.treelineinteractive.recruitmenttask.ui.view.recycler

import androidx.recyclerview.widget.RecyclerView
import com.treelineinteractive.recruitmenttask.R
import com.treelineinteractive.recruitmenttask.data.network.model.Product
import com.treelineinteractive.recruitmenttask.databinding.ViewProductItemBinding

class ProductViewHolder(
    private val binding: ViewProductItemBinding,
    private val onIncClicked: (product: Product) -> Unit,
    private val onDecClicked: (product: Product) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        val context = binding.root.context
        binding.run {
            name.text = context.getString(R.string.item_name, product.title)
            type.text = context.getString(R.string.item_type, product.type)
            color.text = context.getString(R.string.item_color, product.color)
            available.text = product.available.toString()

            incButton.setOnClickListener {
                if (product.available > 0) {
                    product.available--
                    product.sold++
                    available.text = product.available.toString()
                    sold.text = product.sold.toString()
                }
                onIncClicked(product)
            }

            decButton.setOnClickListener {
                if (product.sold > 0) {
                    product.available++
                    product.sold--
                    available.text = product.available.toString()
                    sold.text = product.sold.toString()
                }
                onDecClicked(product)
            }
        }
    }
}