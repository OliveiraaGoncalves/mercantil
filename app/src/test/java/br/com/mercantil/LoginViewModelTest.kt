package br.com.mercantil

import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.domain.AuthUseCase
import br.com.mercantil.feature.presentation.login.LoginViewModel
import br.com.mercantil.feature.presentation.login.LoginViewState
import io.mockk.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var useCase: AuthUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = LoginViewModel(useCase)
    }

    @Test
    fun `should emit Error when username or password is blank`() = runTest {
        viewModel.onLoginClick("", "")
        assertEquals(LoginViewState.Error("Usuário e senha são obrigatórios"), viewModel.viewState.value)
    }

    @Test
    fun `should emit Success with encrypted model when credentials are valid`() = runTest {
        val inputModel = AuthModel("user", "pass")
        val encryptedModel = AuthModel("encryptedUser", "encryptedPass")

        every { useCase.encrypt(inputModel) } returns encryptedModel

        viewModel.onLoginClick("user", "pass")

        assertEquals(LoginViewState.Success(encryptedModel), viewModel.viewState.value)
    }

    @Test
    fun `resetState should emit Idle`() = runTest {
        viewModel.resetState()
        assertEquals(LoginViewState.Idle, viewModel.viewState.value)
    }
}