package com.treelineinteractive.recruitmenttask.ui.main

import android.content.ActivityNotFoundException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.treelineinteractive.recruitmenttask.R
import com.treelineinteractive.recruitmenttask.data.di.component.DaggerActivityComponent
import com.treelineinteractive.recruitmenttask.data.di.module.ActivityModule
import com.treelineinteractive.recruitmenttask.databinding.ActivityMainBinding
import com.treelineinteractive.recruitmenttask.ui.view.recycler.ProductDataAdapter
import com.treelineinteractive.recruitmenttask.utils.observer
import com.treelineinteractive.recruitmenttask.utils.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: ProductDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        injectDependency()
        initView()
        initListeners()
        setObservers()
        mainViewModel.loadProducts()
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.collectReport()
    }

    private fun injectDependency() {
        val component = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()
        component.inject(this)
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

        binding.sendReportButton.setOnClickListener {
            try {
                startActivity(mainViewModel.onReportButtonClicked())
            } catch (activityNotFoundException: ActivityNotFoundException) {
                val snackBar = Snackbar.make(
                    binding.root,
                    getString(R.string.snack_bar_error),
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                snackBar.show()
            }
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