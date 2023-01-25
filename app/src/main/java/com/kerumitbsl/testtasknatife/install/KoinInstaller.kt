package com.kerumitbsl.testtasknatife.install

import android.app.Application
import com.kerumitbsl.core.CoreApiBuilder
import com.kerumitbsl.testtasknatife.AppManager
import com.kerumitbsl.testtasknatife.ui.fullscreenFragment.FullScreenViewModel
import com.kerumitbsl.testtasknatife.ui.mainActivity.MainActivityViewModel
import com.kerumitbsl.testtasknatife.ui.mainFragment.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinInstaller {
    private val appModule = module {
        single { androidApplication() as AppManager }
        //single { AppSettings(get()) }
        single(createdAtStart = true) { CoreApiBuilder.build() }
    }

    private val viewModelModule = module {
        viewModel { MainActivityViewModel() }
        viewModel { FullScreenViewModel() }
        viewModel { MainViewModel() }
    }

    fun install(application: Application) {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(appModule)
            modules(viewModelModule)
        }
    }
}