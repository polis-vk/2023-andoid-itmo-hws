package ru.ok.itmo.tamtam.ioc.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ok.itmo.tamtam.ioc.scope.AppComponentScope
import ru.ok.itmo.tamtam.presentation.stateholder.LoginViewModel
import ru.ok.itmo.tamtam.utils.ViewModelFactory
import ru.ok.itmo.tamtam.utils.ViewModelKey

@Module
interface ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(LoginViewModel::class)]
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @AppComponentScope
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}