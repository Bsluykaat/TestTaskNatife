package com.kerumitbsl.core

import com.kerumitbsl.core.bean.response.GetGifsResponse
import com.kerumitbsl.core.bean.response.TestTaskResponse
import com.kerumitbsl.core.components.GifsLoadingComponent
import com.kerumitbsl.core.other.SingleLiveEvent
import org.koin.core.KoinComponent
import org.koin.core.inject

class CoreApi : KoinComponent {

    private val gifsLoadingComponent: GifsLoadingComponent by inject()

    fun loadGifs(limit: Int, offset: Int, id: String) {
        gifsLoadingComponent.loadTrending(limit, offset, id)
    }

    fun getTrendingLiveData(): SingleLiveEvent<TestTaskResponse<GetGifsResponse>> {
        return gifsLoadingComponent.trendingLiveData
    }

    fun searchGifs(limit: Int, offset: Int, id: String, lang: String, q: String) {
        gifsLoadingComponent.loadSearching(limit, offset, id, lang, q)
    }

    fun getSearchingLiveData(): SingleLiveEvent<TestTaskResponse<GetGifsResponse>> {
        return gifsLoadingComponent.searchingLiveData
    }
}