package com.kerumitbsl.testtasknatife.extensions

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.ui.views.GPHMediaView
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.R

fun loadGif(srcUrl: String, imageView: ImageView) {
    Glide.with(imageView.context).load(srcUrl).placeholder(R.drawable.ic_play).into(imageView)
}

fun loadGifFromGIPHY(gifObject: GifObject, gifView: GPHMediaView) {
    gifView.setMediaWithId(gifObject.id, RenditionType.downsized, AppCompatResources.getDrawable(gifView.context, R.drawable.ic_play))
}

fun loadGifFromGIPHY(id: String, gifView: GPHMediaView) {
    gifView.setMediaWithId(id, RenditionType.downsized, AppCompatResources.getDrawable(gifView.context, R.drawable.ic_play))
}