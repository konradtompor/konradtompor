package com.treelineinteractive.recruitmenttask.ui.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.treelineinteractive.recruitmenttask.data.network.model.Product
import com.treelineinteractive.recruitmenttask.databinding.ViewProductItemBinding

class ProductDataAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private val products: ArrayList<Product> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding =
            ViewProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateData(products: List<Product>) {
        this.notifyChanges(products)
    }

    private fun notifyChanges(productsList: List<Product>) {
        products.clear()
        products.addAll(productsList)
        notifyDataSetChanged()
    }

}