package com.example.iso3.encryption.encryptor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iso3.encryption.data.encriptors.Iso3Encryptor

abstract class PinEncryptorViewModel : ViewModel() {
    // LiveData for errors regarding the PIN
    abstract val pinErrorLiveData: LiveData<Boolean>

    // LiveData for the PIN itself
    abstract val pinLiveData: LiveData<String>

    // LiveData for the Clear PIN Block
    abstract val clearPinBlockLiveData: LiveData<String?>

    abstract fun pinChanged(newPin: String)
    abstract fun generateClearPinBlock()

}

class PinEncryptorViewModelImpl(
    private val encryptor: Iso3Encryptor
) : PinEncryptorViewModel() {
    override val pinErrorLiveData = MutableLiveData<Boolean>() // doesn't start at all
    override val pinLiveData = MutableLiveData("") // starts as empty
    override val clearPinBlockLiveData = MutableLiveData<String?>() // doesn't start at all

    override fun generateClearPinBlock() {
        val pin = pinLiveData.value ?: ""
        if (isPinValid(pin)) {
            pinErrorLiveData.postValue(false)

            // PIN Block
            val pinBlock = encryptor.createPinBlock(pin = pin)

            // Adjusted PAN
            val adjustedPAN = encryptor.adjustPanWithRightMost12Digits(HARDCODED_PAN)

            // Clear PIN Block
            val clearPinBlock = encryptor.createClearPinBlock(
                pinBlock = pinBlock,
                pan = adjustedPAN
            )

            clearPinBlockLiveData.postValue(clearPinBlock)
        } else {
            pinErrorLiveData.postValue(true)
            clearPinBlockLiveData.postValue(null)
        }
    }

    override fun pinChanged(newPin: String) {
        pinLiveData.value = newPin
    }

    private fun isPinValid(pin: String): Boolean {
        return pin.length in 4..12
    }

    companion object {
        private const val HARDCODED_PAN = "1111222233334444"
    }

}