package geekbarains.material.view.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import geekbarains.material.databinding.RecyclerviewActivityBinding

class RecyclerViewActivity: AppCompatActivity() {

    private var isExpanded = false
    private lateinit var binding: RecyclerviewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecyclerviewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFAB()
//        binding.scroll_view.setOnScrollChangeListener { _, _, _, _, _ ->
//            toolbar.isSelected = scroll_view.canScrollVertically(-1)
//        }
    }

    private fun setFAB() {
        setInitialState()
        binding.fab.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFAB()
            }
        }
    }

    private fun setInitialState() {
        binding.transparentBackground.apply {
            alpha = 0f
        }
        binding.optionOneContainer.apply {
            alpha = 0f
            isClickable = false
        }
        binding.optionTwoContainer.apply {
            alpha = 0f
            isClickable = false
        }
        binding.optionThreeContainer.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() {
        isExpanded = true
        binding.also {
            ObjectAnimator.ofFloat(it.plusImageview, "rotation", 0f, 225f).start()
            ObjectAnimator.ofFloat(it.optionOneContainer, "translationX", -200f).start()
            ObjectAnimator.ofFloat(it.optionTwoContainer, "translationY", -200f).start()

            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationY", -141f).start()
            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationX", -141f).start()

            it.optionThreeContainer.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.optionThreeContainer.isClickable = true
                            it.optionThreeContainer.setOnClickListener {
                                Toast.makeText(this@RecyclerViewActivity, "Option 3", Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }
                    })
            it.optionTwoContainer.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.optionTwoContainer.isClickable = true
                            it.optionTwoContainer.setOnClickListener {
                                Toast.makeText(this@RecyclerViewActivity, "Option 2", Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }
                    })
            it.optionOneContainer.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.optionOneContainer.isClickable = true
                            it.optionOneContainer.setOnClickListener {
                                Toast.makeText(this@RecyclerViewActivity, "Option 1", Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }
                    })

            it.transparentBackground.animate()
                .alpha(0.9f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        it.transparentBackground.isClickable = true
                    }
                })
        }
    }


    private fun collapseFab() {
        isExpanded = false
        binding.also {
            ObjectAnimator.ofFloat(it.plusImageview, "rotation", 0f, -180f).start()
            ObjectAnimator.ofFloat(it.optionTwoContainer, "translationY", 0f).start()
            ObjectAnimator.ofFloat(it.optionOneContainer, "translationX", 0f).start()
            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationY", 0f).start()
            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationX", 0f).start()

            it.optionThreeContainer.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.optionThreeContainer.isClickable = false
                            it.optionThreeContainer.setOnClickListener(null)
                        }
                    })
            it.optionTwoContainer.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.optionTwoContainer.isClickable = false
                            it.optionTwoContainer.setOnClickListener(null)
                        }
                    })
            it.optionOneContainer.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.optionOneContainer.isClickable = false
                        }
                    })

            it.transparentBackground.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            it.transparentBackground.isClickable = false
                        }
                    })
        }
    }

}