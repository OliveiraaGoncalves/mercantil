package br.com.mercantil.core

import android.util.Base64

interface CryptoManager {
    fun encrypt(value: String): String
    fun decrypt(value: String): String
}

class CryptoManagerImpl : CryptoManager {

    override fun encrypt(value: String): String {
        return Base64.encodeToString(value.toByteArray(), Base64.NO_WRAP)
    }

    override fun decrypt(value: String): String {
        return String(Base64.decode(value, Base64.NO_WRAP), Charsets.UTF_8)
    }

}