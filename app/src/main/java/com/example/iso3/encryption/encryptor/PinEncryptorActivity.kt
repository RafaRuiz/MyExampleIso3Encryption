package com.example.iso3.encryption.encryptor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.iso3.encryption.R
import kotlinx.android.synthetic.main.activity_pin_encryptor.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PinEncryptorActivity : AppCompatActivity() {

    private val viewModel: PinEncryptorViewModel by viewModel()

    private val generateButton by lazy { generate_button }
    private val pinTextInputLayout by lazy { pin_text_input_layout }
    private val pinEditText by lazy { pin_edit_text }
    private val clearPinBlockText by lazy { clear_pin_block_text }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_encryptor)

        setClickListeners()
        setObservers()
        setWatcher()
    }

    private val errorObserver = Observer<Boolean> { error ->
        if (error) {
            pinTextInputLayout.error = getString(R.string.error_please_digits_minimum_maximum)
        } else {
            pinTextInputLayout.error = null
        }
    }

    private val clearPinBlockObserver = Observer<String?> { clearPinBlock ->
        if (clearPinBlock.isNullOrBlank()) {
            clearPinBlockText.text = null
        } else {
            clearPinBlockText.text = getString(R.string.your_clear_pin_block_is_s, clearPinBlock)
        }
    }

    private fun setObservers() {
        viewModel.pinErrorLiveData.observe(this, errorObserver)
        viewModel.clearPinBlockLiveData.observe(this, clearPinBlockObserver)
    }

    private fun setClickListeners() {
        generateButton.setOnClickListener {
            viewModel.generateClearPinBlock()
        }
    }

    private fun setWatcher() {
        pinEditText.addTextChangedListener {
            viewModel.pinChanged(it?.toString() ?: "")
        }
    }
}