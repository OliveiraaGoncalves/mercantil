package br.com.mercantil.feature.domain

import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.data.AuthRepository

interface AuthUseCase {
    fun isValidLogin(authModel: AuthModel): Boolean

    fun saveLog(authModel: AuthModel)

    fun encrypt(authModel: AuthModel): AuthModel

    fun decrypt(authModel: AuthModel): AuthModel
}

class AuthUseCaseImpl(
    private val repository: AuthRepository
) : AuthUseCase {

    override fun isValidLogin(authModel: AuthModel): Boolean {
        val modelDecripted = decrypt(authModel)
        val resultValidation =
            modelDecripted.user == repository.getUserLocal() && modelDecripted.password == repository.getPasswordLocal()
        if (resultValidation) {
            saveLog(modelDecripted)
        }
        return resultValidation
    }

    override fun saveLog(authModel: AuthModel) {
        repository.saveLog(authModel)
    }

    override fun encrypt(authModel: AuthModel): AuthModel {
        return repository.encrypt(authModel)
    }

    override fun decrypt(authModel: AuthModel): AuthModel {
        return repository.decrypt(authModel)
    }


}