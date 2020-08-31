package com.example.iso3.ui.encryption

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.iso3.ui.encryption.data.encriptors.Iso3Encryptor
import com.example.iso3.ui.encryption.encryptor.PinEncryptorViewModel
import com.example.iso3.ui.encryption.encryptor.PinEncryptorViewModelImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PinEncryptorViewModelTests {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var subject: PinEncryptorViewModel

    @Before
    fun setUp() {
        subject = PinEncryptorViewModelImpl(Iso3Encryptor())
    }

    @Test
    fun `pin error doesn't have any value at start`() {
        assert(subject.pinErrorLiveData.value == null)
    }

    @Test
    fun `pin has an empty value at start`() {
        assert(subject.pinLiveData.value?.isEmpty() == true)
    }

    @Test
    fun `clear pin block has no value at all at start`() {
        assert(subject.clearPinBlockLiveData.value == null)
    }

    @Test
    fun `on pin changed, the live data is updated`() {
        subject.pinChanged("1234")

        assert(subject.pinLiveData.value == "1234")
    }

    @Test
    fun `generating a clear pin block on an empty pin - fails`() {
        subject.pinChanged("")

        subject.generateClearPinBlock()

        // There's an error
        assert(subject.pinErrorLiveData.value == true)
        // There's no clear pin block at all
        assert(subject.clearPinBlockLiveData.value == null)
    }

    @Test
    fun `generating a clear pin block on more than 12 digits - fails`() {
        subject.pinChanged("1234567890123")

        subject.generateClearPinBlock()

        // There's an error
        assert(subject.pinErrorLiveData.value == true)
        // There's no clear pin block at all
        assert(subject.clearPinBlockLiveData.value == null)
    }

    @Test
    fun `generating a clear pin block on less than 4 digits - fails`() {
        subject.pinChanged("123")

        subject.generateClearPinBlock()

        // There's an error
        assert(subject.pinErrorLiveData.value == true)
        // There's no clear pin block at all
        assert(subject.clearPinBlockLiveData.value == null)
    }

    @Test
    fun `generating a clear pin block with 5 digits - succeeds`() {
        subject.pinChanged("12345")

        subject.generateClearPinBlock()

        // There's an error
        assert(subject.pinErrorLiveData.value == false)
        // There's no clear pin block at all
        assert(subject.clearPinBlockLiveData.value != null)
    }
}