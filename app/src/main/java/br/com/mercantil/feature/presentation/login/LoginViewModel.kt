package br.com.mercantil.feature.presentation.login

import androidx.lifecycle.ViewModel
import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.domain.AuthUseCase

class LoginViewModel(
    private val useCase: AuthUseCase
) : ViewModel() {

    fun encrypt(user: String, password: String): AuthModel {
        return useCase.encrypt(AuthModel(user = user, password = password))
    }
}