package com.kerumitbsl.core.install

import com.google.gson.Gson
import com.kerumitbsl.core.components.rest.HttpCommunicationComponent
import com.kerumitbsl.core.components.GifsLoadingComponent
import org.koin.core.context.loadKoinModules
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class KoinCoreInstaller {

    private val componentModule = module {
        single { GifsLoadingComponent(get(), get()) }
        single { HttpCommunicationComponent(get()) }
    }

    private val supportComponents = module {
        single { Gson() }
    }

    fun install() {
        koinApplication {
            loadKoinModules(listOf(componentModule, supportComponents))
        }
    }
}