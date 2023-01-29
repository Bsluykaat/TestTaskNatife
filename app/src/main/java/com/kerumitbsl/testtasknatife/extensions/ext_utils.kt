package com.kerumitbsl.testtasknatife.extensions

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService


fun isNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}