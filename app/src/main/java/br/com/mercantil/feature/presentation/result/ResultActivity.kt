package br.com.mercantil.feature.presentation.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.mercantil.databinding.ActivityResultBinding
import br.com.mercantil.feature.presentation.validation.ValidationActivity.Companion.KEY_EXTRA_RESULT
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val result = intent.getBooleanExtra(KEY_EXTRA_RESULT, false)

        viewModel.setResult(result)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resultState
                    .filterNotNull()
                    .collect { uiState ->
                        binding.textViewResult.text = uiState.message
                        binding.imageViewIcon.setImageResource(uiState.iconRes)
                    }
            }
        }
    }
}