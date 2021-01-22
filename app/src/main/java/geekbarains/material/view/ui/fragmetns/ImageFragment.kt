package geekbarains.material.view.ui.fragmetns

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import geekbarains.material.R
import geekbarains.material.databinding.FragmentImageBinding
import geekbarains.material.model.entity.LoadedData
import geekbarains.material.model.entity.LoadedDataImpl
import geekbarains.material.model.entity.PictureOfTheDayData
import geekbarains.material.viewmodel.ImageViewModel

class ImageFragment: Fragment() {

    private val loadedData: LoadedData = LoadedDataImpl
    private var binding: FragmentImageBinding? = null
    private var paramDay: Int = 0
    private var paramIndex: Int = 0

    private val viewModel: ImageViewModel by lazy {
        ViewModelProvider(this).get(ImageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentImageBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.run {
            paramDay = getInt(EXTRA_DATA_DAY)
            paramIndex = getInt(EXTRA_DATA_INDEX)
            loadedData.loadData(paramIndex)?.run {
                renderData(this)
            } ?: run {
                viewModel.getData(paramDay).observe(viewLifecycleOwner,  { renderData(it) })
            }
        } ?: run {
            toast("Извините данные не переданы")
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.singleUrl
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    binding?.run {
                        loadedData.saveData(paramIndex, data)
                        imageView.load(url) {
                            lifecycle(this@ImageFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
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
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        private val EXTRA_DATA_DAY = ImageFragment::class.java.name + "EXTRA_DATA_DAY"
        private val EXTRA_DATA_INDEX = ImageFragment::class.java.name + "EXTRA_DATA_INDEX"

        fun instance(day: Int, index: Int) = ImageFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_DATA_DAY, day)
                putInt(EXTRA_DATA_INDEX, index)
            }
        }
    }
}