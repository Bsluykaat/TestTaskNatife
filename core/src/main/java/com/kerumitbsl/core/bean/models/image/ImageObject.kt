package com.kerumitbsl.core.bean.models.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageObject(
    val fixed_height: FixedHeightObject
): Parcelable