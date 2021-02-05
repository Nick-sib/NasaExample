package geekbarains.material.view.ui.activities

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import geekbarains.material.DESCRIPTION_LENGTH
import geekbarains.material.R
import geekbarains.material.databinding.AnimationsTransformStartActivityBinding
import geekbarains.material.model.entity.picture.PictureOfTheDayData
import geekbarains.material.viewmodel.ImageViewModel



class AnimationsTransformActivity: AppCompatActivity() {

    private lateinit var binding: AnimationsTransformStartActivityBinding
    private var show = false

    private val viewModel: ImageViewModel by lazy {
        ViewModelProvider(this@AnimationsTransformActivity).get(ImageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AnimationsTransformStartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniView()
    }

    private fun iniView(){
        with(binding){
            backgroundImage.setOnClickListener {
                if (show)
                    hideComponents()
                else
                    showComponents()
            }

            viewModel.getData().observe(this@AnimationsTransformActivity,  { renderData(it) })

        }
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
                        backgroundImage.load(url) {
                            lifecycle(this@AnimationsTransformActivity)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                        tvDate.text = serverResponseData.date ?: "i'm don't know"
                        tvTitle.text = serverResponseData.title ?: "Without"
                        tvDescription.text = serverResponseData
                            .explanation.substring(0, DESCRIPTION_LENGTH)
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

    private fun showComponents() {
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.animations_transform_end_activity)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }

    private fun hideComponents() {
        show = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.animations_transform_start_activity)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(binding.constraintContainer, transition)
        constraintSet.applyTo(binding.constraintContainer)
    }
}