package br.com.mercantil.feature.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.mercantil.databinding.LoginActivityBinding
import br.com.mercantil.feature.presentation.validation.ValidationActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private val viewModel: LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val user = binding.editTextUsername.text.toString()
            val pass = binding.editTextPassword.text.toString()

            viewModel.onLoginClick(user, pass)
        }

        observeViewState()
    }

    private fun observeViewState() {
        lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                when (state) {
                    is LoginViewState.Idle -> Unit
                    is LoginViewState.Success -> {
                        val intent =
                            Intent(this@LoginActivity, ValidationActivity::class.java).apply {
                                putExtra(KEY_EXTRA_AUTH_MODEL, state.authModel)
                            }
                        startActivity(intent)
                        viewModel.resetState()
                    }

                    is LoginViewState.Error -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                        viewModel.resetState()
                    }
                }
            }
        }
    }

    companion object {
        const val KEY_EXTRA_AUTH_MODEL = "auth_model"
    }
}