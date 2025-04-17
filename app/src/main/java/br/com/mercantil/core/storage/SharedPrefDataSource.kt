package br.com.mercantil.core.storage

import android.content.SharedPreferences

interface SharedPreferencesDataSource {
    fun getString(key: String): String
    fun putString(key: String, value: String)
}

class SharedPreferencesDataSourceImpl(
    private val encryptedPrefs: SharedPreferences
): SharedPreferencesDataSource {

    override fun getString(key: String): String {
        return encryptedPrefs.getString(key, "") ?: ""
    }

    override fun putString(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }
}