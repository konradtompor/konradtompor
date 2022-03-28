package com.treelineinteractive.recruitmenttask.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.treelineinteractive.recruitmenttask.databinding.ActivityMainBinding
import com.treelineinteractive.recruitmenttask.ui.view.recycler.ProductDataAdapter
import com.treelineinteractive.recruitmenttask.utils.observer
import com.treelineinteractive.recruitmenttask.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val mainViewModel = MainViewModel()

    private lateinit var adapter: ProductDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListeners()
        setObservers()
        mainViewModel.loadProducts()
    }

    private fun initView() {
        adapter = ProductDataAdapter(
            onIncClicked = mainViewModel::onIncButtonClicked,
            onDecClicked = mainViewModel::onDecButtonClicked
        )
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = linearLayoutManager
    }

    private fun initListeners() {
        binding.retryButton.setOnClickListener {
            mainViewModel.loadProducts()
        }
    }

    private fun setObservers() {
        mainViewModel.products.observe(this) { products ->
            adapter.updateData(products)
        }

        mainViewModel.stateLiveData.observe { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.errorLayout.isVisible = state.error != null
            binding.errorLabel.text = state.error
        }
    }

    private fun <T> LiveData<T>.observe(onChanged: (T) -> Unit) {
        observer(this@MainActivity, onChanged)
    }
}