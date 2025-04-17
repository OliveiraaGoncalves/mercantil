package br.com.mercantil.feature.presentation.result

import androidx.lifecycle.ViewModel
import br.com.mercantil.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResultViewModel : ViewModel() {

    private val _resultState = MutableStateFlow<ResultUiState?>(null)
    val resultState: StateFlow<ResultUiState?> = _resultState

    fun setResult(result: Boolean) {
        _resultState.value = if (result) {
            ResultUiState.Success("Login realizado com sucesso!", R.drawable.ic_success)
        } else {
            ResultUiState.Error("Falha na autenticação.", R.drawable.ic_failure)
        }
    }
}

sealed class ResultUiState(val message: String, val iconRes: Int) {
    class Success(message: String, iconRes: Int) : ResultUiState(message, iconRes)
    class Error(message: String, iconRes: Int) : ResultUiState(message, iconRes)
}