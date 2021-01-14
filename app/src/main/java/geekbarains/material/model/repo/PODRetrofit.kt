package geekbarains.material.model.repo

import geekbarains.material.model.api.PictureOfTheDayAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient

interface PODRetrofit {
    fun getRetrofitImpl(): PictureOfTheDayAPI
}