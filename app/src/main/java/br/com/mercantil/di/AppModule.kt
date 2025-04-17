package br.com.mercantil.di

import br.com.mercantil.feature.data.AuthRepository
import br.com.mercantil.feature.data.AuthRepositoryImpl
import br.com.mercantil.feature.domain.AuthUseCase
import br.com.mercantil.feature.domain.AuthUseCaseImpl
import br.com.mercantil.feature.presentation.login.LoginViewModel
import br.com.mercantil.feature.presentation.result.ResultViewModel
import br.com.mercantil.feature.presentation.validation.ValidationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val instance = module {
        viewModel {
            LoginViewModel(
                useCase = get()
            )
        }

        viewModel {
            ValidationViewModel(
                useCase = get()
            )
        }

        viewModel {
            ResultViewModel()
        }

        single<AuthUseCase> {
            AuthUseCaseImpl(
                repository = get()
            )
        }

        single<AuthRepository> {
            AuthRepositoryImpl(
                sharedPreferencesDataSource = get(),
                cryptoManager = get()
            )
        }
    }
}