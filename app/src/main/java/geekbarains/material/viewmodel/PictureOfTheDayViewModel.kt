package geekbarains.material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.BuildConfig
import geekbarains.material.model.repo.PODRetrofit
import geekbarains.material.model.repo.retrofit.PODRetrofitImpl
import geekbarains.material.model.entity.PODServerResponseData
import geekbarains.material.model.entity.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
        private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
        private val retrofit: PODRetrofit = PODRetrofitImpl()
) :
    ViewModel() {

    fun getData(): LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            produceError("You need API key")
        } else {
            retrofit.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(
                object : Callback<PODServerResponseData> {
                    override fun onResponse(
                            call: Call<PODServerResponseData>,
                            response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.run {
                                liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Success(this)
                            } ?: run {
                                produceError(response.message())
                            }
                        } else {
                            produceError(response.message())
                        }
                    }
                    override fun onFailure(
                            call: Call<PODServerResponseData>,
                            t: Throwable
                    ) = produceError(t.message)
                }
            )
        }
    }

    fun sendServerRequest(date: String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            produceError("You need API key")
        } else {
            retrofit.getRetrofitImpl().getPictureOfTheDay(date, apiKey).enqueue(
                    object : Callback<PODServerResponseData> {
                        override fun onResponse(
                                call: Call<PODServerResponseData>,
                                response: Response<PODServerResponseData>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.run {
                                    liveDataForViewToObserve.value =
                                            PictureOfTheDayData.Success(this)
                                } ?: run {
                                    produceError(response.message())
                                }
                            } else {
                                produceError(response.message())
                            }
                        }
                        override fun onFailure(
                                call: Call<PODServerResponseData>,
                                t: Throwable
                        ) = produceError(t.message)
                    }
            )
        }
    }

    fun produceError(message: String?) {
        if (message.isNullOrEmpty()) {
            liveDataForViewToObserve.value =
                    PictureOfTheDayData.Error(Throwable("Unidentified error"))
        } else {
            liveDataForViewToObserve.value =
                    PictureOfTheDayData.Error(Throwable(message))
        }
    }
}
