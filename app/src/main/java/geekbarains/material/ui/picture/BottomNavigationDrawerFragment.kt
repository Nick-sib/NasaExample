package geekbarains.material.ui.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import geekbarains.material.R
import geekbarains.material.databinding.BottomNavigationLayoutBinding

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
//        {
//        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.run {
            navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_one -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                    R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }

    }
}
