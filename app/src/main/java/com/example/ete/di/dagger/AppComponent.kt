package com.example.ete.di.dagger

import com.example.ete.di.MyApplication
import com.example.ete.di.viewmodel.BaseViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface AppComponent {

    fun inject(myApplication: MyApplication)
    fun inject(viewModel: BaseViewModel)
}