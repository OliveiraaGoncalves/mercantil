package br.com.mercantil.feature.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.mercantil.databinding.LoginActivityBinding
import br.com.mercantil.feature.presentation.validation.ValidationActivity
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

            val intent = Intent(this, ValidationActivity::class.java)
            intent.putExtra(KEY_EXTRA_AUTH_MODEL, viewModel.encrypt(user, pass))
            startActivity(intent)
        }
    }

    companion object {
        const val KEY_EXTRA_AUTH_MODEL = "auth_model"
    }
}