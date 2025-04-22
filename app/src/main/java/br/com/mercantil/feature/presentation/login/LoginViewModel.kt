package br.com.mercantil.feature.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.domain.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginViewState {
    object Idle : LoginViewState()
    data class Success(val authModel: AuthModel) : LoginViewState()
    data class Error(val message: String) : LoginViewState()
}

class LoginViewModel(
    private val useCase: AuthUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.Idle)
    val viewState: StateFlow<LoginViewState> = _viewState

    fun onLoginClick(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _viewState.value = LoginViewState.Error("Usuário e senha são obrigatórios")
            return
        }

        val encryptedModel = useCase.encrypt(AuthModel(user = username, password = password))
        _viewState.value = LoginViewState.Success(encryptedModel)
    }

    fun resetState() {
        _viewState.value = LoginViewState.Idle
    }
}