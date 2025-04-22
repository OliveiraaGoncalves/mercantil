package br.com.mercantil

import br.com.mercantil.core.CryptoManager
import br.com.mercantil.core.storage.SharedPreferencesDataSource
import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.data.AuthRepository
import br.com.mercantil.feature.data.AuthRepositoryImpl
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {

    private lateinit var repository: AuthRepository
    private val sharedPrefs: SharedPreferencesDataSource = mockk(relaxed = true)
    private val cryptoManager: CryptoManager = mockk()

    @Before
    fun setUp() {
        repository = AuthRepositoryImpl(sharedPrefs, cryptoManager)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should return predefined user`() {
        val result = repository.getUserLocal()
        assertEquals("Teste01", result)
    }

    @Test
    fun `should return predefined password`() {
        val result = repository.getPasswordLocal()
        assertEquals("Mercantil", result)
    }

    @Test
    fun `should save log to shared preferences`() {
        val authModel = AuthModel(user = "user", password = "pass")

        repository.saveLog(authModel)

        verify(exactly = 1) {
            sharedPrefs.putString(any(), eq("user"))
        }
    }

    @Test
    fun `should encrypt user and password`() {
        every { cryptoManager.encrypt("user") } returns "encryptedUser"
        every { cryptoManager.encrypt("pass") } returns "encryptedPass"

        val authModel = AuthModel(user = "user", password = "pass")
        val result = repository.encrypt(authModel)

        assertEquals("encryptedUser", result.user)
        assertEquals("encryptedPass", result.password)

        verify { cryptoManager.encrypt("user") }
        verify { cryptoManager.encrypt("pass") }
    }

    @Test
    fun `should decrypt user and password`() {
        every { cryptoManager.decrypt("encryptedUser") } returns "user"
        every { cryptoManager.decrypt("encryptedPass") } returns "pass"

        val authModel = AuthModel(user = "encryptedUser", password = "encryptedPass")
        val result = repository.decrypt(authModel)

        assertEquals("user", result.user)
        assertEquals("pass", result.password)

        verify { cryptoManager.decrypt("encryptedUser") }
        verify { cryptoManager.decrypt("encryptedPass") }
    }
}