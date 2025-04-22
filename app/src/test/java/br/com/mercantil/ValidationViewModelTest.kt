package br.com.mercantil

import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.domain.AuthUseCase
import br.com.mercantil.feature.presentation.validation.ValidationViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ValidationViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var useCase: AuthUseCase
    private lateinit var viewModel: ValidationViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk()
        viewModel = ValidationViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validateLogin should emit true when login is valid`() = runTest {
        val authModel = AuthModel("validUser", "validPass")
        coEvery { useCase.isValidLogin(authModel) } returns true

        viewModel.validateLogin("validUser", "validPass")

        advanceTimeBy(1500)
        advanceUntilIdle()

        assertEquals(true, viewModel.validationResult.value)
    }

    @Test
    fun `validateLogin should emit false when login is invalid`() = runTest {
        val authModel = AuthModel("invalidUser", "invalidPass")
        coEvery { useCase.isValidLogin(authModel) } returns false

        viewModel.validateLogin("invalidUser", "invalidPass")

        advanceTimeBy(1500)
        advanceUntilIdle()

        assertEquals(false, viewModel.validationResult.value)
    }

    @Test
    fun `initial state should be null`() {
        assertNull(viewModel.validationResult.value)
    }
}