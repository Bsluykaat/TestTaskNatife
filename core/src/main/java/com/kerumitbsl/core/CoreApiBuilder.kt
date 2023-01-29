package com.kerumitbsl.core

import com.kerumitbsl.core.install.KoinCoreInstaller


object CoreApiBuilder {

    fun build(): CoreApi {
        KoinCoreInstaller().install()
        return CoreApi()
    }
}