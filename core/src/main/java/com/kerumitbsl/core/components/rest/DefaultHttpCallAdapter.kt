package com.kerumitbsl.core.components.rest

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kerumitbsl.core.bean.BackendError
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

class DefaultHttpCallAdapter(private val gson: Gson) : CallAdapter.Factory() {

    companion object {

        private const val HTTP_LOGICAL_ERROR: Int = 400
        private const val HTTP_AUTH_ERROR: Int = 401
        private const val HTTP_FORBIDDEN: Int = 403
        private const val HTTP_NOT_FOUND: Int = 404
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
        return object : CallAdapter<Any?, Any?> {
            override fun responseType(): Type {
                return returnType
            }

            override fun adapt(call: Call<Any?>): Any {
                try {
                    val response = call.execute()

                    if (response.isSuccessful) return response.body() ?: Unit

                    throw try {
                        val errorBody = response.errorBody()!!.string()

                        when {
                            response.code() == HTTP_AUTH_ERROR || response.code() == HTTP_FORBIDDEN -> {
                                BackendError.Authorization(details = errorBody)
                            }
                            response.code() == HTTP_LOGICAL_ERROR -> {
                                gson.fromJson(errorBody, BackendError.Logical::class.java)
                            }
                            response.code() == HTTP_NOT_FOUND -> {
                                BackendError.NotFound(details = errorBody)
                            }
                            else -> {
                                BackendError.Unknown(details = errorBody)
                            }
                        }
                    } catch (e: IOException) {
                        throw BackendError.System(details = e.message ?: "")
                    } catch (e: JsonSyntaxException) {
                        throw BackendError.System(details = e.message ?: "")
                    }
                } catch (e: IOException) {
                    throw BackendError.Connectivity(details = e.message ?: "")
                }
            }
        }
    }
}
