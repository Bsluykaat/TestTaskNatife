package com.kerumitbsl.testtasknatife.base

import androidx.lifecycle.AndroidViewModel
import com.kerumitbsl.core.CoreApi
import com.kerumitbsl.testtasknatife.AppManager
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel : AndroidViewModel(AppManager()), KoinComponent {

    protected val coreApi: CoreApi by inject()

}