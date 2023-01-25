package com.kerumitbsl.core.bean.response

import android.os.Parcelable
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.core.bean.models.MetaObject
import com.kerumitbsl.core.bean.models.PaginationObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetGifsResponse(
    val data: Array<GifObject>,
    val pagination: PaginationObject,
    val meta: MetaObject
) : Parcelable