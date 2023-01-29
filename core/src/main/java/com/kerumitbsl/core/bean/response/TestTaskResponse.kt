package com.kerumitbsl.core.bean.response

import com.kerumitbsl.core.bean.models.MetaObject


sealed class TestTaskResponse<out T> {

    class Success<T>(val data: T) : TestTaskResponse<T>()
    class Error(val meta: MetaObject) : TestTaskResponse<Nothing>()
}

