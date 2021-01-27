package geekbarains.material.view.ui.activities

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import geekbarains.material.R
import geekbarains.material.databinding.AnimationsTransformStartActivityBinding

class AnimationsTransformActivity: AppCompatActivity() {

    private lateinit var binding: AnimationsTransformStartActivityBinding

    private var show = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AnimationsTransformStartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backgroundImage.setOnClickListener {
            if (show)
                hideComponents()
            else
                showComponents()
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