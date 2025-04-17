package br.com.mercantil.feature.data

import java.io.Serializable

data class AuthModel(val user: String, val password: String) : Serializable
