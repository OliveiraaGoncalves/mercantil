package br.com.mercantil.core.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import br.com.mercantil.core.CryptoManager
import br.com.mercantil.core.CryptoManagerImpl
import br.com.mercantil.core.storage.SharedPreferencesDataSource
import br.com.mercantil.core.storage.SharedPreferencesDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object StorageModule {
    val instance = module {
        single<SharedPreferencesDataSource>() {
            SharedPreferencesDataSourceImpl(
                encryptedPrefs = EncryptedSharedPreferences.create(
                    "secure_prefs",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    androidContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            )
        }

        single<CryptoManager> {
            CryptoManagerImpl()
        }
    }
}