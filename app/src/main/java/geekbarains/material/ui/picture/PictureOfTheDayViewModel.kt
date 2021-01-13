package geekbarains.material.ui.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbarains.material.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
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
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(
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
