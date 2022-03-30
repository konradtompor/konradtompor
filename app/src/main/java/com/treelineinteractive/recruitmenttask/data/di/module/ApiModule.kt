package com.treelineinteractive.recruitmenttask.data.di.module

import com.treelineinteractive.recruitmenttask.data.repository.ShopRepository
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideShopRepository() = ShopRepository()
}