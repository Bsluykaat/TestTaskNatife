package com.kerumitbsl.core.extensions

import org.json.JSONObject

fun JSONObject.isEmpty(): Boolean {
    return length() == 0
}

fun JSONObject.isNotEmpty(): Boolean {
    return !isEmpty()
}