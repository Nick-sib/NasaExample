package geekbarains.material.view.ui.fragmetns

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import geekbarains.material.R
import geekbarains.material.databinding.MainFragmentBinding
import geekbarains.material.model.entity.PictureOfTheDayData
import geekbarains.material.model.entity.getDate
import geekbarains.material.view.ui.MainActivity
import geekbarains.material.viewmodel.PictureOfTheDayViewModel

import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var binding: MainFragmentBinding? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetView: ConstraintLayout


    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(viewLifecycleOwner,  { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater, container, false).let{
        binding = it
        it.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

            chipGroupPictureDay.setOnCheckedChangeListener { _, checkedId ->
                viewModel.sendServerRequest(
                    getDate(when(checkedId) {
                        1 -> -2
                        2 -> -1
                        else -> 0}
                ))
            }
        }

        setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_bottom_sheet -> {
                bottomSheetView.visibility = View.VISIBLE
                changeBottomSheetState()
            }
            R.id.app_bar_fav -> {
                toast("Favourite")
            }
            R.id.app_bar_settings ->
                parentFragmentManager.beginTransaction().add(R.id.container, SettingFragment()).addToBackStack(null).commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.singleUrl
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    binding?.run {
                        imageView.load(url) {
                            lifecycle(this@PictureOfTheDayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                    }
                    bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_description_header).text =
                            serverResponseData.title
                    bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_description).text =
                            serverResponseData.explanation
                    bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_date).text =
                            serverResponseData.date
                    bottomSheetView.findViewById<TextView>(R.id.bottom_sheet_copyright).text =
                            serverResponseData.copyright
                }
            }
            is PictureOfTheDayData.Loading -> {
                Log.d("myLOG", "renderData: ${data.progress}")
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
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

    private fun toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun instance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
