package com.kerumitbsl.testtasknatife

import android.app.Application
import com.giphy.sdk.ui.Giphy
import com.kerumitbsl.core.extensions.GIPHY_INIT_KEY
import com.kerumitbsl.testtasknatife.install.KoinInstaller

class AppManager : Application() {

    override fun onCreate() {
        KoinInstaller().install(this)
        Giphy.configure(this, GIPHY_INIT_KEY)
        super.onCreate()
    }
}