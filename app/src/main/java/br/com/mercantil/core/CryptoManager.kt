package br.com.mercantil.core

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

interface CryptoManager {
    fun encrypt(plainText: String): String
    fun decrypt(encryptedText: String): String
}

class CryptoManagerImpl : CryptoManager {

    private fun generateSecretKey(): SecretKey {
        return SecretKeySpec(AES_KEY_STRING.toByteArray(CHARSET), "AES")
    }

    override fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)

        val initializationVector = ByteArray(IV_SIZE_BYTES).apply {
            SecureRandom().nextBytes(this)
        }

        val ivSpec = IvParameterSpec(initializationVector)
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(), ivSpec)

        val encryptedBytes = cipher.doFinal(plainText.toByteArray(CHARSET))

        val ivAndEncrypted = initializationVector + encryptedBytes

        return Base64.encodeToString(ivAndEncrypted, Base64.NO_WRAP)
    }

    override fun decrypt(encryptedText: String): String {
        val ivAndEncrypted = Base64.decode(encryptedText, Base64.NO_WRAP)

        val iv = ivAndEncrypted.copyOfRange(0, IV_SIZE_BYTES)
        val encryptedBytes = ivAndEncrypted.copyOfRange(IV_SIZE_BYTES, ivAndEncrypted.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, generateSecretKey(), ivSpec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes, CHARSET)
    }

    companion object {
        private const val AES_KEY_STRING =  "1234567890123456"
        private val CHARSET = Charsets.UTF_8
        private val TRANSFORMATION = "AES/CBC/PKCS5Padding"
        private val IV_SIZE_BYTES = 16

    }
}