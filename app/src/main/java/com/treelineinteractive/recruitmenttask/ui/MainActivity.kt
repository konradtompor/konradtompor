package com.treelineinteractive.recruitmenttask.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import com.treelineinteractive.recruitmenttask.databinding.ActivityMainBinding
import com.treelineinteractive.recruitmenttask.ui.view.ProductItemView

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainViewModel.loadProducts()

        binding.retryButton.setOnClickListener {
            mainViewModel.loadProducts()
        }

        mainViewModel.stateLiveData.observe { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.errorLayout.isVisible = state.error != null
            binding.errorLabel.text = state.error

            binding.itemsLayout.isVisible = state.isSuccess

            if (state.isSuccess) {
                binding.itemsLayout.removeAllViews()

                state.items.forEach {
                    val itemView = ProductItemView(this)
                    itemView.setProductItem(it)
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    params.topMargin = 16
                    params.marginEnd = 16
                    params.marginStart = 16

                    itemView.layoutParams = params

                    binding.itemsLayout.addView(itemView, binding.itemsLayout.childCount)
                }
            }
        }
    }

    fun <T> LiveData<T>.observe(onChanged: (T) -> Unit) {
        observer(this@MainActivity, onChanged)
    }
}