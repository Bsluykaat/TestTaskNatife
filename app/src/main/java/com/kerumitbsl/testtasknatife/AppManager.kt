package com.kerumitbsl.testtasknatife

import android.app.Application
import android.net.Uri
import com.facebook.cache.common.CacheKey
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.cache.disk.FileCache
import com.facebook.imagepipeline.cache.CacheKeyFactory
import com.facebook.imagepipeline.core.FileCacheFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.request.ImageRequest
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.GiphyFrescoHandler
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.core.extensions.GIPHY_INIT_KEY
import com.kerumitbsl.testtasknatife.extensions.CACHED_IDS_LIST_KEY
import com.kerumitbsl.testtasknatife.extensions.FORBIDDEN_IDS_LIST_KEY
import com.kerumitbsl.testtasknatife.install.KoinInstaller
import com.orhanobut.hawk.Hawk
import okhttp3.OkHttpClient

class AppManager : Application() {

    override fun onCreate() {

        Hawk.init(this).build()
        Hawk.put(CACHED_IDS_LIST_KEY, mutableListOf<GifObject>())
        Hawk.put(FORBIDDEN_IDS_LIST_KEY, mutableListOf<GifObject>())

        KoinInstaller().install(this)


        Giphy.configure(this, GIPHY_INIT_KEY, frescoHandler = object : GiphyFrescoHandler {
            override fun handle(imagePipelineConfigBuilder: ImagePipelineConfig.Builder) {
                // according to documentation
                imagePipelineConfigBuilder
                    .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(applicationContext)
                            .setMaxCacheSize(1000000)
                            .setMaxCacheSizeOnLowDiskSpace(500000)
                            .setMaxCacheSizeOnVeryLowDiskSpace(100000)
                            .build()
                    )
            }
            override fun handle(okHttpClientBuilder: OkHttpClient.Builder) {
            }
        })

        super.onCreate()
    }
}