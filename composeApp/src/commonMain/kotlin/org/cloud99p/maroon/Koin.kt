package org.cloud99p.maroon

import org.koin.core.module.Module

expect class KoinInitializer {
    fun init()
}

expect val appViewModelModule: Module
expect val dataStoreModule: Module
expect val dataBaseModule: Module
