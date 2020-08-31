package com.example.iso3.encryption.data

import com.example.iso3.encryption.data.encriptors.Iso3Encryptor
import org.koin.dsl.module

val encryptorModule = module {
    single { Iso3Encryptor() }
}