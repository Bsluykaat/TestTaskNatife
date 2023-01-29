package com.kerumitbsl.core

import com.kerumitbsl.core.bean.response.GetGifsResponse
import com.kerumitbsl.core.bean.response.TestTaskResponse
import com.kerumitbsl.core.components.GifsLoadingComponent
import com.kerumitbsl.core.other.SingleLiveEvent
import org.koin.core.KoinComponent
import org.koin.core.inject

class CoreApi : KoinComponent {

    private val gifsLoadingComponent: GifsLoadingComponent by inject()

    fun loadGifs(limit: Int, offset: Int, rating: String, id: String, bundle: String) {
        gifsLoadingComponent.loadTrending(limit, offset, rating, id, bundle)
    }

    fun getTrendingLiveData(): SingleLiveEvent<TestTaskResponse<GetGifsResponse>> {
        return gifsLoadingComponent.trendingLiveData
    }

    fun searchGifs(q: String, limit: Int, offset: Int, rating: String, lang: String, id: String, bundle: String) {
        gifsLoadingComponent.loadSearching(q, limit, offset, rating, lang, id, bundle)
    }

    fun getSearchingLiveData(): SingleLiveEvent<TestTaskResponse<GetGifsResponse>> {
        return gifsLoadingComponent.searchingLiveData
    }
}