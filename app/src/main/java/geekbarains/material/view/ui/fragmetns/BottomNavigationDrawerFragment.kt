package geekbarains.material.view.ui.fragmetns

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import geekbarains.material.R
import geekbarains.material.databinding.BottomNavigationLayoutBinding
import geekbarains.material.view.ui.activities.AnimationsMotionActivity
import geekbarains.material.view.ui.activities.AnimationsTransformActivity
import geekbarains.material.view.ui.activities.RecyclerViewActivity

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var binding: BottomNavigationLayoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = BottomNavigationLayoutBinding.inflate(inflater, container, false).let{
        binding = it
        it.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.run {
            navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_one -> activity?.let {
                        startActivity(
                                Intent(it,AnimationsTransformActivity::class.java)
                        )
                    }
                    R.id.navigation_two ->  activity?.let {
                        startActivity(
                                Intent(it, AnimationsMotionActivity::class.java)
                        )
                    }
                    R.id.navigation_three ->  activity?.let {
                        startActivity(
                                Intent(it, RecyclerViewActivity::class.java)
                        )
                    }
                }
                true
            }
        }

    }
}
