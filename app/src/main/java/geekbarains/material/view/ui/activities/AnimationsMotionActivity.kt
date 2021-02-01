package geekbarains.material.view.ui.activities

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import geekbarains.material.DESCRIPTION_LENGTH
import geekbarains.material.R
import geekbarains.material.databinding.MotionActivityBinding
import geekbarains.material.model.entity.PictureOfTheDayData
import geekbarains.material.viewmodel.ImageViewModel

class AnimationsMotionActivity: AppCompatActivity() {//R.layout.motion_activity
    //TODO: есть смысл создать родительский абстрактный класс с render и toast
    private lateinit var binding: MotionActivityBinding
    private val viewModel: ImageViewModel by lazy {
        ViewModelProvider(this@AnimationsMotionActivity).get(ImageViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MotionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniView()
    }

    private fun iniView(){
        viewModel.getData().observe(this@AnimationsMotionActivity,  { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.singleUrl
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    binding.run {
//                        findViewById<ImageView>(R.id.iv_background)?.also {
//                            it.load(url) {
//                                lifecycle(this@AnimationsMotionActivity)
//                                kotlin.error(R.drawable.ic_load_error_vector)
//                                placeholder(R.drawable.ic_no_photo_vector)
//                            }
//                        }
                        findViewById<TextView>(R.id.iv_title)?.run{
                            text = serverResponseData.title ?: "i'm don't know"
                        }
                        findViewById<TextView>(R.id.tv_subtitle)?.run{
                            text = serverResponseData.copyright ?: "Without"
                        }
                        findViewById<TextView>(R.id.tv_info)?.run{
                            text = serverResponseData.explanation ?:  "Without"
                        }
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                //Log.d("myLOG", "renderData: ${data.progress}")
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun toast(string: String?) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }


}




