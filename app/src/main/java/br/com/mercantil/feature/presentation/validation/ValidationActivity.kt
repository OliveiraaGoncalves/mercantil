package br.com.mercantil.feature.presentation.validation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.mercantil.databinding.ValidationActivityBinding
import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.presentation.login.LoginActivity.Companion.KEY_EXTRA_AUTH_MODEL
import br.com.mercantil.feature.presentation.result.ResultActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ValidationActivity : AppCompatActivity() {
    private lateinit var binding: ValidationActivityBinding

    private val viewModel: ValidationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ValidationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = intent.getSerializableExtra(KEY_EXTRA_AUTH_MODEL) as? AuthModel

        auth?.let {
            viewModel.validateLogin(user = auth.user, password = auth.password)
        } ?: run {
            startResultActivity(USER_NOT_LOGGED)
        }

        observer()
    }

    private fun observer() {
        lifecycleScope.launch {
            viewModel.validationResult.collect { isValid ->
                isValid?.let {
                    startResultActivity(it)
                }
            }
        }
    }

    private fun startResultActivity(isLoggedSuccess: Boolean) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(KEY_EXTRA_RESULT, isLoggedSuccess)
        startActivity(intent)
        finish()
    }

    companion object {
        const val KEY_EXTRA_RESULT = "result"
        const val USER_NOT_LOGGED = false
    }
}