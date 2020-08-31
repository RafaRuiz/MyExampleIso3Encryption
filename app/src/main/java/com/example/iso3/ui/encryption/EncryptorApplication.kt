package com.example.iso3.ui.encryption

import android.app.Application
import com.example.iso3.ui.encryption.data.encryptorModule
import com.example.iso3.ui.encryption.modules.viewModelModule
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