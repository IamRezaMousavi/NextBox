package org.cloud99p.nextbox

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun initKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            appViewModelModule,
            dataStoreModule,
            dataBaseModule
        )
    }
}

val appViewModelModule = module {
    viewModelOf(::AppViewModel)
}

expect val dataStoreModule: Module
expect val dataBaseModule: Module
