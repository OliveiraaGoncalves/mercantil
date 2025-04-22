package br.com.mercantil

import br.com.mercantil.feature.presentation.result.ResultUiState
import br.com.mercantil.feature.presentation.result.ResultViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ResultViewModelTest {

    private lateinit var viewModel: ResultViewModel

    @Before
    fun setup() {
        viewModel = ResultViewModel()
    }

    @Test
    fun `setResult true should emit Success state`() = runTest {
        viewModel.setResult(true)

        val result = viewModel.resultState.value
        assert(result is ResultUiState.Success)
        assertEquals("Login realizado com sucesso!", result?.message)
        assertEquals(R.drawable.ic_success, result?.iconRes)
    }

    @Test
    fun `setResult false should emit Error state`() = runTest {
        viewModel.setResult(false)

        val result = viewModel.resultState.value
        assert(result is ResultUiState.Error)
        assertEquals("Falha na autenticação.", result?.message)
        assertEquals(R.drawable.ic_failure, result?.iconRes)
    }

    @Test
    fun `initial state should be null`() = runTest {
        assertEquals(null, viewModel.resultState.value)
    }
}