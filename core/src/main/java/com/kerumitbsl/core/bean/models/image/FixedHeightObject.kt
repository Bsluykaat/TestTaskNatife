package com.kerumitbsl.core.bean.models.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FixedHeightObject(
    val url: String,
    val width: String,
    val height: String,
    val size: String,
    val mp4: String,
    val mp4Size: String,
    val webp: String,
    val webp_size: String
) : Parcelable