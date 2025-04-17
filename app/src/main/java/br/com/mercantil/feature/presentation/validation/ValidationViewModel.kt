package br.com.mercantil.feature.presentation.validation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.domain.AuthUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ValidationViewModel(
    private val useCase: AuthUseCase
) : ViewModel()  {
    private val _validationResult = MutableStateFlow<Boolean?>(null)
    val validationResult: StateFlow<Boolean?> = _validationResult
    
    fun validateLogin(user: String, password: String) {
        viewModelScope.launch {
            delay(1500)

            val result = useCase.isValidLogin(AuthModel(user, password))
            _validationResult.value = result
        }
    }
}