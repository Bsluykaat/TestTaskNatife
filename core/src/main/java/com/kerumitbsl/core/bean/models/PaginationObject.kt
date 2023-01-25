package com.kerumitbsl.core.bean.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaginationObject(
    val offset: Int,
    val total_count: Int,
    val count: Int
) : Parcelable