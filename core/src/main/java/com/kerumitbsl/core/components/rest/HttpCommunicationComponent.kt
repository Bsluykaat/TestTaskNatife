package com.kerumitbsl.core.components.rest

import android.util.Log
import com.google.gson.Gson
import com.kerumitbsl.core.BuildConfig
import com.kerumitbsl.core.bean.BackendError
import com.kerumitbsl.core.bean.models.MetaObject
//import com.kerumitbsl.core.extensions.BASE_URL_SEARCHING
import com.kerumitbsl.core.extensions.BASE_URL_TRENDING
import com.kerumitbsl.core.bean.response.TestTaskResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpCommunicationComponent(
    gson: Gson,
) {

    private val okHttpLoggingInterceptor = HttpLoggingInterceptor { message: String? ->
        if (message != null) {
            Log.d(TAG, message)
        }
    }.apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(DefaultHttpInterceptor())
        .addInterceptor(okHttpLoggingInterceptor)
        .connectTimeout(DEFAULT_REQUEST_DELAY, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_REQUEST_DELAY, TimeUnit.SECONDS)
        .build()

    private val trendingRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_TRENDING)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(DefaultHttpCallAdapter(gson))
        .build()

    /*private val searchingRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_SEARCHING)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(DefaultHttpCallAdapter(gson))
        .build()*/

    fun <T> createTrendingService(cl: Class<T>): T = trendingRetrofit.create(cl)
    //fun <T> createSearchingService(cl: Class<T>): T = searchingRetrofit.create(cl)

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> execute(request: suspend () -> Any?): TestTaskResponse<T> {
        val result = runCatching { request() }

        return when {
            result.isSuccess -> TestTaskResponse.Success(result.getOrThrow() as T)
            result.isFailure -> when (result.exceptionOrNull()) {
                is BackendError -> TestTaskResponse.Error(MetaObject("", "", null))
                else -> TestTaskResponse.Error(MetaObject("", "", null))
            }
            else -> TestTaskResponse.Error(MetaObject("", "", null))
        }
    }

    companion object {
        private const val TAG = "OkHttp"
        private const val DEFAULT_REQUEST_DELAY = 30L
    }
}