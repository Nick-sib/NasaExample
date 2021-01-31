package geekbarains.material.view.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import geekbarains.material.databinding.RecyclerviewActivityBinding
import geekbarains.material.getEarthDescription
import geekbarains.material.getEarthTitle
import geekbarains.material.model.entity.DataPlanet
import geekbarains.material.model.entity.PLANET_TYPE_EARTH
import geekbarains.material.model.entity.PLANET_TYPE_JUPITER
import geekbarains.material.model.entity.PLANET_TYPE_MARS
import geekbarains.material.view.ui.adapters.recyclerview.adapter.ItemTouchHelperCallback
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.OnStartDragListener
import geekbarains.material.view.ui.adapters.recyclerview.adapter.PlanetsRecyclerviewAdapter


private const val ANIM_DURATION = 300L
private const val ANIM_LINEAR_OFFSET = -200f
private const val ANIM_ANGLE_OFFSET = -141f
private const val BACKGROUND_ALPHA = 0.4f
private const val NONTRANSPARENT_ALPHA = 1f
private const val TRANSPARENT_ALPHA = 0f

class RecyclerViewActivity: AppCompatActivity() {

    private lateinit var adapter: PlanetsRecyclerviewAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

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
            {showMessage(it)},
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setInitialState() {
        binding.transparentBackground.apply {
            alpha = TRANSPARENT_ALPHA
        }
        binding.optionMarsContainer.apply {
            alpha = TRANSPARENT_ALPHA
            isClickable = false
        }
        binding.optionEarthContainer.apply {
            alpha = TRANSPARENT_ALPHA
            isClickable = false
        }
        binding.optionJupiterContainer.apply {
            alpha = TRANSPARENT_ALPHA
            isClickable = false
        }
    }

    private fun expandFAB() {
        binding.also {
            ObjectAnimator.ofFloat(it.plusImageview, "rotation", 0f, 225f).start()
            ObjectAnimator.ofFloat(it.optionMarsContainer, "translationX", ANIM_LINEAR_OFFSET).start()
            ObjectAnimator.ofFloat(it.optionEarthContainer, "translationY", ANIM_LINEAR_OFFSET).start()
            ObjectAnimator.ofFloat(it.optionJupiterContainer, "translationY", ANIM_ANGLE_OFFSET).start()
            ObjectAnimator.ofFloat(it.optionJupiterContainer, "translationX", ANIM_ANGLE_OFFSET).start()

            doAnimation(it.optionJupiterContainer, NONTRANSPARENT_ALPHA, true) {
                adapter.addItem(
                        DataPlanet(
                                type = PLANET_TYPE_JUPITER
                        ))
            }
            doAnimation(it.optionEarthContainer, NONTRANSPARENT_ALPHA, true) {
                adapter.addItem(
                        DataPlanet(
                                someText = getEarthTitle(),
                                someDescription = getEarthDescription(),
                                type = PLANET_TYPE_EARTH
                        ))
            }
            doAnimation(it.optionMarsContainer, NONTRANSPARENT_ALPHA, true) {
                adapter.addItem(
                        DataPlanet(
                                type = PLANET_TYPE_MARS
                        ))
            }
            doAnimation(it.transparentBackground, BACKGROUND_ALPHA, true) {
                isExpanded = false
            }
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
            ObjectAnimator.ofFloat(it.optionEarthContainer, "translationY", 0f).start()
            ObjectAnimator.ofFloat(it.optionMarsContainer, "translationX", 0f).start()
            ObjectAnimator.ofFloat(it.optionJupiterContainer, "translationY", 0f).start()
            ObjectAnimator.ofFloat(it.optionJupiterContainer, "translationX", 0f).start()

            doAnimation(it.optionMarsContainer, TRANSPARENT_ALPHA, false)
            doAnimation(it.optionEarthContainer, TRANSPARENT_ALPHA, false)
            doAnimation(it.optionJupiterContainer, TRANSPARENT_ALPHA, false)
            doAnimation(it.transparentBackground, TRANSPARENT_ALPHA, false)
        }
    }

    private fun showMessage(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).apply {
            setAction("Ok"){
                this.dismiss()
            }
            show()
        }
    }

}