package com.kerumitbsl.testtasknatife.ui.mainActivity

import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.base.BaseViewModel
import com.kerumitbsl.testtasknatife.extensions.CACHED_IDS_LIST_KEY
import com.orhanobut.hawk.Hawk

class MainActivityViewModel : BaseViewModel() {

    var content = mutableListOf<GifObject>()
    set(value) {
        Hawk.put(CACHED_IDS_LIST_KEY, value)
    }

}