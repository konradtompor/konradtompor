package com.treelineinteractive.recruitmenttask.data.di.component

import com.treelineinteractive.recruitmenttask.data.di.module.ActivityModule
import com.treelineinteractive.recruitmenttask.data.di.module.ApiModule
import com.treelineinteractive.recruitmenttask.ui.main.MainActivity
import dagger.Component

@Component(modules = [ActivityModule::class, ApiModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}