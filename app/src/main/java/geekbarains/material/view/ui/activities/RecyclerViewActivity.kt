package geekbarains.material.view.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import geekbarains.material.databinding.RecyclerviewActivityBinding
import geekbarains.material.model.entity.DataPlanet
import geekbarains.material.model.entity.PLANET_TYPE_MARS
import geekbarains.material.view.ui.adapters.recyclerview.adapter.PlanetsRecyclerviewAdapter


private const val ANIM_DURATION = 300L
private const val ANIM_LINEAR_OFFSET = -200f
private const val ANIM_ANGLE_OFFSET = -141f
private const val BACKGROUND_ALPHA = 0.4f
private const val NONTRANSPARENT_ALPHA = 1f
private const val TRANSPARENT_ALPHA = 0f

class RecyclerViewActivity: AppCompatActivity() {

    private lateinit var adapter: PlanetsRecyclerviewAdapter

    private var isExpanded = false
        set(value) {
            if (value) expandFAB() else collapseFab()
            field = value
        }
    private lateinit var binding: RecyclerviewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecyclerviewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFAB()
        setAdapter()
    }

    private fun setFAB() {
        setInitialState()
        binding.fab.setOnClickListener {
            isExpanded = !isExpanded
        }
    }

    private fun setAdapter() {
        val data = arrayListOf(
            Pair(DataPlanet(0, "Header"), false),
            Pair(DataPlanet(1, "Mars", "", PLANET_TYPE_MARS), false),
        )

        adapter = PlanetsRecyclerviewAdapter(
            data,
        )
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setInitialState() {
        binding.transparentBackground.apply {
            alpha = TRANSPARENT_ALPHA
        }
        binding.optionOneContainer.apply {
            alpha = TRANSPARENT_ALPHA
            isClickable = false
        }
        binding.optionTwoContainer.apply {
            alpha = TRANSPARENT_ALPHA
            isClickable = false
        }
        binding.optionThreeContainer.apply {
            alpha = TRANSPARENT_ALPHA
            isClickable = false
        }
    }

    private fun expandFAB() {
        binding.also {
            ObjectAnimator.ofFloat(it.plusImageview, "rotation", 0f, 225f).start()
            ObjectAnimator.ofFloat(it.optionOneContainer, "translationX", ANIM_LINEAR_OFFSET).start()
            ObjectAnimator.ofFloat(it.optionTwoContainer, "translationY", ANIM_LINEAR_OFFSET).start()
            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationY", ANIM_ANGLE_OFFSET).start()
            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationX", ANIM_ANGLE_OFFSET).start()

            doAnimation(it.optionThreeContainer, NONTRANSPARENT_ALPHA, true) {
                Toast.makeText(this@RecyclerViewActivity, "Option 3", Toast.LENGTH_SHORT)
                        .show()
            }
            doAnimation(it.optionTwoContainer, NONTRANSPARENT_ALPHA, true) {
                Toast.makeText(this@RecyclerViewActivity, "Option 2", Toast.LENGTH_SHORT)
                        .show()
            }
            doAnimation(it.optionOneContainer, NONTRANSPARENT_ALPHA, true) {
                Toast.makeText(this@RecyclerViewActivity, "Option 1", Toast.LENGTH_SHORT)
                        .show()
            }
            doAnimation(it.transparentBackground, BACKGROUND_ALPHA, true)
        }
    }

    private fun doAnimation(view: View, alpha: Float, isClickable: Boolean, action: (() -> Unit)? = null){
        view.animate()
            .alpha(alpha)
            .setDuration(ANIM_DURATION)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.isClickable = isClickable
                    action?.run {
                        view.setOnClickListener {
                            this.invoke()
                        }
                    }
                }
        })
    }


    private fun collapseFab() {
        binding.also {
            ObjectAnimator.ofFloat(it.plusImageview, "rotation", 0f, -180f).start()
            ObjectAnimator.ofFloat(it.optionTwoContainer, "translationY", 0f).start()
            ObjectAnimator.ofFloat(it.optionOneContainer, "translationX", 0f).start()
            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationY", 0f).start()
            ObjectAnimator.ofFloat(it.optionThreeContainer, "translationX", 0f).start()

            doAnimation(it.optionOneContainer, TRANSPARENT_ALPHA, false)
            doAnimation(it.optionTwoContainer, TRANSPARENT_ALPHA, false)
            doAnimation(it.optionThreeContainer, TRANSPARENT_ALPHA, false)
            doAnimation(it.transparentBackground, TRANSPARENT_ALPHA, true)
        }
    }

}