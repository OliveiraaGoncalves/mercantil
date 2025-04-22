package br.com.mercantil

import br.com.mercantil.feature.data.AuthModel
import br.com.mercantil.feature.data.AuthRepository
import br.com.mercantil.feature.domain.AuthUseCase
import br.com.mercantil.feature.domain.AuthUseCaseImpl
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthUseCaseImplTest {

    private val repository: AuthRepository = mockk()
    private lateinit var useCase: AuthUseCase

    @Before
    fun setUp() {
        useCase = AuthUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should return true and save log when credentials match`() {
        val inputModel = AuthModel(user = "encryptedUser", password = "encryptedPass")
        val decryptedModel = AuthModel(user = "Teste01", password = "Mercantil")

        every { repository.decrypt(inputModel) } returns decryptedModel
        every { repository.getUserLocal() } returns "Teste01"
        every { repository.getPasswordLocal() } returns "Mercantil"
        every { repository.saveLog(decryptedModel) } just Runs

        val result = useCase.isValidLogin(inputModel)

        assertTrue(result)
        verify { repository.saveLog(decryptedModel) }
    }

    @Test
    fun `should return false and not save log when credentials do not match`() {
        val inputModel = AuthModel(user = "encryptedUser", password = "encryptedPass")
        val decryptedModel = AuthModel(user = "WrongUser", password = "WrongPass")

        every { repository.decrypt(inputModel) } returns decryptedModel
        every { repository.getUserLocal() } returns "Teste01"
        every { repository.getPasswordLocal() } returns "Mercantil"

        val result = useCase.isValidLogin(inputModel)

        assertFalse(result)
        verify(exactly = 0) { repository.saveLog(any()) }
    }

    @Test
    fun `should encrypt authModel`() {
        val model = AuthModel("user", "pass")
        val encrypted = AuthModel("encryptedUser", "encryptedPass")

        every { repository.encrypt(model) } returns encrypted

        val result = useCase.encrypt(model)

        assertEquals(encrypted, result)
    }

    @Test
    fun `should decrypt authModel`() {
        val encrypted = AuthModel("encUser", "encPass")
        val decrypted = AuthModel("user", "pass")

        every { repository.decrypt(encrypted) } returns decrypted

        val result = useCase.decrypt(encrypted)

        assertEquals(decrypted, result)
    }

    @Test
    fun `should delegate saveLog to repository`() {
        val model = AuthModel("user", "pass")
        every { repository.saveLog(model) } just Runs

        useCase.saveLog(model)

        verify { repository.saveLog(model) }
    }
}