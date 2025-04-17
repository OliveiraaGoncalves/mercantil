package br.com.mercantil

import android.content.SharedPreferences
import br.com.mercantil.core.storage.SharedPreferencesDataSource
import br.com.mercantil.core.storage.SharedPreferencesDataSourceImpl
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class SharedPreferencesDataSourceTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dataSource: SharedPreferencesDataSource

    @Before
    fun setUp() {
        sharedPreferences = mockk()
        editor = mockk()
        dataSource = SharedPreferencesDataSourceImpl(sharedPreferences)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getString should return stored value`() {
        val key = "test_key"
        val expectedValue = "test_value"
        every { sharedPreferences.getString(key, "") } returns expectedValue

        val result = dataSource.getString(key)

        assertEquals(expectedValue, result)
        verify(exactly = 1) { sharedPreferences.getString(key, "") }
    }

    @Test
    fun `putString should store value using SharedPreferences editor`() {
        val key = "test_key"
        val value = "test_value"

        every { sharedPreferences.edit() } returns editor
        every { editor.putString(key, value) } returns editor
        every { editor.apply() } just Runs

        dataSource.putString(key, value)

        verifyOrder {
            sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }
}