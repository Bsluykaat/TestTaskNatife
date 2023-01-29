package com.kerumitbsl.testtasknatife.ui.mainFragment

import androidx.lifecycle.viewModelScope
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.core.bean.response.GetGifsResponse
import com.kerumitbsl.core.bean.response.TestTaskResponse
import com.kerumitbsl.core.extensions.LIMIT_ON_PAGE
import com.kerumitbsl.core.other.SingleLiveEvent
import com.kerumitbsl.testtasknatife.base.BaseViewModel
import com.kerumitbsl.testtasknatife.extensions.FORBIDDEN_IDS_LIST_KEY
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    val trendingLiveData: SingleLiveEvent<TestTaskResponse<GetGifsResponse>> get() = coreApi.getTrendingLiveData()

    val searchingLiveData: SingleLiveEvent<TestTaskResponse<GetGifsResponse>> get() = coreApi.getSearchingLiveData()

    private var offset = 0

    var lang = "en"
    var id = ""
    var q: String = ""

    fun refreshPagination() {
        offset = 0
    }

    fun requestTrendingGifs(rating: String = "g", id: String, bundle: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            coreApi.loadGifs(LIMIT_ON_PAGE, offset, rating, id, bundle)
            offset += LIMIT_ON_PAGE
        }
    }

    fun requestSearchingGifs(
        q: String,
        rating: String = "g",
        lang: String = this.lang,
        id: String,
        bundle: String = ""
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            coreApi.searchGifs(q, LIMIT_ON_PAGE, offset, rating, lang, id, bundle)
            offset += LIMIT_ON_PAGE
        }
    }

    fun filterContent(list: List<GifObject>): List<GifObject> {
        val forbiddenContentList = Hawk.get<MutableList<GifObject>>(FORBIDDEN_IDS_LIST_KEY)
        return list.filter { !forbiddenContentList.contains(it) }
    }
}