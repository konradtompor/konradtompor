package com.treelineinteractive.recruitmenttask.data.di.module

import android.app.Activity
import com.treelineinteractive.recruitmenttask.data.repository.ShopRepository
import com.treelineinteractive.recruitmenttask.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideViewModel(shopRepository: ShopRepository): MainViewModel {
        return MainViewModel(shopRepository)
    }
}