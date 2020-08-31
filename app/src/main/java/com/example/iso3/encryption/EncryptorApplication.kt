package com.example.iso3.encryption

import android.app.Application
import com.example.iso3.encryption.data.encryptorModule
import com.example.iso3.encryption.modules.viewModelModule
import org.koin.core.context.startKoin

class EncryptorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            modules(
                listOf(
                    encryptorModule,
                    viewModelModule
                )
            )
        }
    }
}