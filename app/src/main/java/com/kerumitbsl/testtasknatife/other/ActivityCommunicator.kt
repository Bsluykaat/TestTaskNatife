package com.kerumitbsl.testtasknatife.other

import androidx.appcompat.widget.SearchView
import com.kerumitbsl.core.bean.models.GifObject

interface ActivityCommunicator {
    fun setContent(list: List<GifObject>)
    fun getContent(): List<GifObject>
    fun getAppBarSearchView(): SearchView
}