package com.kerumitbsl.core.bean.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserObject(
    val avatar_url: String,
    val banner_url: String,
    val profile_url: String,
    val username: String,
    val display_name: String
) : Parcelable