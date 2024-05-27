package com.example.telinhas.di

import com.example.telinhas.authentication.login.LoginFragment
import com.example.telinhas.authentication.viewmodel.LoginViewModel
import com.example.telinhas.authentication.viewmodel.RecoverViewModel
import com.example.telinhas.authentication.viewmodel.RegisterViewModel
import com.example.telinhas.domain.usecase.RecoverAuthenticationUseCase
import com.example.telinhas.domain.usecase.RegisterUseCase
import com.example.telinhas.domain.usecase.VerifyLoginUseCase
import com.example.telinhas.home.viewmodel.HomeViewModel
import com.example.telinhas.presentation.PresentationFragment
import com.example.telinhas.presentation.viewmodel.PresentationViewModel
import com.example.telinhas.repository.AuthRepository
import com.example.telinhas.repository.RecoverAuthenticationRepository
import com.example.telinhas.repository.RecoverAuthenticationRepositoryImpl
import com.example.telinhas.repository.RegisterRepository
import com.example.telinhas.repository.RegisterRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { PresentationViewModel(get()) }
    viewModel { RecoverViewModel(get()) }
    viewModel { RegisterViewModel(get()) }

    factory { VerifyLoginUseCase(get()) }
    factory { AuthRepository() }
    factory { RecoverAuthenticationUseCase(get(), Dispatchers.IO) }
    factory { RegisterUseCase(get()) }
    factory<RecoverAuthenticationRepository> { RecoverAuthenticationRepositoryImpl() }
    factory<RegisterRepository> { RegisterRepositoryImpl(get()) }

    single {
        LoginFragment.newInstance()
    }

    single {
        PresentationFragment.newInstance()
    }

    single {
        LoginFragment.newInstance()
    }
}