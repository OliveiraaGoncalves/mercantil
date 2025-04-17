package br.com.mercantil.feature.data

import br.com.mercantil.core.storage.SharedPreferencesDataSource
import java.util.UUID
import br.com.mercantil.core.CryptoManager

interface AuthRepository {
    fun getUserLocal(): String

    fun getPasswordLocal(): String

    fun saveLog(authModel: AuthModel)

    fun encrypt(authModel: AuthModel): AuthModel

    fun decrypt(authModel: AuthModel): AuthModel
}

class AuthRepositoryImpl(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource,
    private val cryptoManager: CryptoManager
) : AuthRepository {

    override fun getUserLocal(): String = predefinedUser

    override fun getPasswordLocal(): String = predefinedPass

    override fun saveLog(authModel: AuthModel) {
        sharedPreferencesDataSource.putString(UUID.randomUUID().toString(), authModel.user)
    }

    override fun encrypt(authModel: AuthModel): AuthModel {
        return AuthModel(
            user = cryptoManager.encrypt(authModel.user),
            password = cryptoManager.encrypt(authModel.password)
        )
    }

    override fun decrypt(authModel: AuthModel): AuthModel {
        return AuthModel(
            user = cryptoManager.decrypt(authModel.user),
            password = cryptoManager.decrypt(authModel.password)
        )
    }

    companion object {
        private val predefinedUser = "Teste01"
        private val predefinedPass = "Mercantil"
    }
}