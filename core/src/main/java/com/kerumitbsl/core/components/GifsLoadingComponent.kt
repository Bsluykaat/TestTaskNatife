package com.kerumitbsl.core.components

import com.google.gson.Gson
import com.kerumitbsl.core.components.rest.HttpCommunicationComponent
import com.kerumitbsl.core.components.rest.TestTaskApi
import com.kerumitbsl.core.bean.models.MetaObject
import com.kerumitbsl.core.bean.response.GetGifsResponse
import com.kerumitbsl.core.extensions.isNotEmpty
import com.kerumitbsl.core.bean.response.TestTaskResponse
import com.kerumitbsl.core.extensions.GIPHY_INIT_KEY
import com.kerumitbsl.core.other.SingleLiveEvent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import org.koin.core.KoinComponent

class GifsLoadingComponent(
    networkComponent: HttpCommunicationComponent,
    private val gson: Gson
) : KoinComponent {

    val trendingLiveData = SingleLiveEvent<TestTaskResponse<GetGifsResponse>>()
    val searchingLiveData = SingleLiveEvent<TestTaskResponse<GetGifsResponse>>()

    private val service = networkComponent.createTrendingService(TestTaskApi::class.java)

    fun loadTrending(limit: Int, offset: Int, id: String) {

        val rs = service.getTrendedGifs(limit = limit, offset = offset, random_id = id)

        /*val response = JSONObject(rs.toString())
        if (response.isNotEmpty() && response.getJSONObject("data").isNotEmpty()) {
            TestTaskResponse.Success(gson.fromJson(response.getString("data"), GetGifsResponse::class.java))
        } else TestTaskResponse.Error(gson.fromJson(response.getString("meta"), MetaObject::class.java))*/

        if (rs.meta.status == "200") {
            trendingLiveData.postValue(TestTaskResponse.Success(rs))
        } else {
            trendingLiveData.postValue(TestTaskResponse.Error(rs.meta))
        }
    }

    fun loadSearching(limit: Int, offset: Int, id: String, lang: String, q: String) {

        val rs = service.getSearchedGifs(q = q, limit = limit, offset = offset, lang = lang, random_id = id)

        /*val response = JSONObject(rs.toString())
        if (response.isNotEmpty() && response.getJSONObject("data").isNotEmpty()) {
            TestTaskResponse.Success(gson.fromJson(response.getString("data"), GetGifsResponse::class.java))
        } else TestTaskResponse.Error(gson.fromJson(response.getString("meta"), MetaObject::class.java))*/
        if (rs.meta.status == "200") {
            searchingLiveData.postValue(TestTaskResponse.Success(rs))
        } else {
            searchingLiveData.postValue(TestTaskResponse.Error(rs.meta))
        }
    }
}