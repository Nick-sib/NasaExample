package geekbarains.material.model.repo

import geekbarains.material.model.api.PictureOfTheDayAPI


interface PODRetrofit {
    fun getRetrofitImpl(): PictureOfTheDayAPI
}