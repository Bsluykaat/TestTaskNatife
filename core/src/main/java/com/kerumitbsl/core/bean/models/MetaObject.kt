package com.kerumitbsl.core.bean.models

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
open class MetaObject(
    val msg: String,
    val status: String,
    val response_id: String?
) : Parcelable {
    companion object {
        fun parse(jsonObject: JSONObject): MetaObject {
            val meta: MetaObject = try {
                Gson().fromJson(jsonObject.toString(), MetaObject::class.java)
            } catch (e: Exception) {
                MetaObject("-1", "something went wrong...", null)
            }
            return meta
        }
    }
}