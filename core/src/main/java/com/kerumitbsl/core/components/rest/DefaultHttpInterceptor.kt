package com.kerumitbsl.core.components.rest

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class DefaultHttpInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()

        newRequestBuilder.addHeader("Content-Type", "application/json")
        //newRequestBuilder.addHeader("Authorization", "Bearer " + dataProvider.getToken())

        return chain.proceed(newRequestBuilder.build())
    }
}