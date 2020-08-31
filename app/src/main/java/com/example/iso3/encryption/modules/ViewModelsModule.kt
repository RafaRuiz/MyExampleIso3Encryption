package com.example.iso3.encryption.modules

import com.example.iso3.encryption.data.encriptors.Iso3Encryptor
import com.example.iso3.encryption.encryptor.PinEncryptorViewModel
import com.example.iso3.encryption.encryptor.PinEncryptorViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<PinEncryptorViewModel> { PinEncryptorViewModelImpl(encryptor = Iso3Encryptor()) }
}