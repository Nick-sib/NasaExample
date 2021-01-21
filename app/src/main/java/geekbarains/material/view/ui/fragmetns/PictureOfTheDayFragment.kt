package geekbarains.material.view.ui.fragmetns

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import geekbarains.material.R
import geekbarains.material.databinding.MainFragmentBinding
import geekbarains.material.model.entity.LoadedData
import geekbarains.material.model.entity.LoadedDataImpl
import geekbarains.material.model.entity.PictureOfTheDayData
import geekbarains.material.view.ui.MainActivity
import geekbarains.material.view.ui.adapters.HistoryPageAdapter


class PictureOfTheDayFragment : Fragment() {

    private var binding: MainFragmentBinding? = null
    val loadedData: LoadedData = LoadedDataImpl
    var currentFragmentIndex: Int = 0


    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetView: ConstraintLayout

    private val bottomSheetDescriptionHeader by lazy {
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_description_header)
    }
    private val bottomSheetDescription by lazy {
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_description)
    }
    private val bottomSheetDate by lazy {
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_date)
    }
    private val bottomSheetCopyright by lazy {
        bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_copyright)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater, container, false).let{
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            inputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
                })
            }
            bottomSheetView = root.findViewById(R.id.bottom_sheet_container)
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
            viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    currentFragmentIndex = position
                    renderData(loadedData.loadData(position))
                }
            })
            HistoryPageAdapter(LoadedDataImpl, childFragmentManager).run {
                viewPager.adapter = this
                viewPager.currentItem = this.count
            }
        }
        setBottomAppBar(view)
    }

    override fun onDestroyView() {
        binding?.run {
            viewPager.clearOnPageChangeListeners()
        }
        super.onDestroyView()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_bottom_sheet -> {
                renderData(loadedData.loadData(currentFragmentIndex))
                bottomSheetView.visibility = View.VISIBLE
                changeBottomSheetState()
            }
            R.id.app_bar_fav -> {
                toast("Favourite")
            }
            R.id.app_bar_settings ->
                parentFragmentManager
                        .beginTransaction()
                        .add(R.id.container, SettingFragment())
                        .addToBackStack(null)
                        .commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureOfTheDayData.Success?) {
        data?.run {
            bottomSheetDescriptionHeader.text = serverResponseData.title
            bottomSheetDescription.text = serverResponseData.explanation
            bottomSheetDate.text = serverResponseData.date
            bottomSheetCopyright.text = serverResponseData.copyright
        }
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding?.run {
            fab.setOnClickListener {
                if (isMain) {
                    isMain = false
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                } else {
                    isMain = true
                    bottomAppBar.navigationIcon =
                        ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
        }
    }

    private fun changeBottomSheetState() {
        with (bottomSheetBehavior){
            state = when (state) {
                BottomSheetBehavior.STATE_HIDDEN,
                BottomSheetBehavior.STATE_COLLAPSED     -> BottomSheetBehavior.STATE_HALF_EXPANDED
                BottomSheetBehavior.STATE_HALF_EXPANDED,
                BottomSheetBehavior.STATE_EXPANDED      -> BottomSheetBehavior.STATE_COLLAPSED
                else -> BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun instance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
