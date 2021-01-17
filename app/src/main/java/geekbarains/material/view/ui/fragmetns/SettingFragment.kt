package geekbarains.material.view.ui.fragmetns

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import geekbarains.material.*
import geekbarains.material.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private var binding: FragmentSettingBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSettingBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run{
            when (App.instance.selectedTheme) {
                THEME_LIGHT -> chipThemeLight.isChecked = true
                THEME_DARK -> chipThemeDark.isChecked = true
                THEME_TOXIC -> chipThemeToxic.isChecked = true
            }

            cgChooseTheme.setOnCheckedChangeListener { _, checkedId ->
                App.instance.selectedTheme =
                        when (cgChooseTheme.checkedChipId) {
                            R.id.chip_theme_light -> 0
                            R.id.chip_theme_dark -> 1
                            R.id.chip_theme_toxic -> {
                                Toast.makeText(
                                    context,
                                    "Простите пока не раелизовано",
                                    Toast.LENGTH_SHORT
                                ).show()
                                2
                            }
                            else -> 0
                        }
            }

//            chipGroup.setOnCheckedChangeListener { chipGroup, position ->
//                chipGroup.findViewById<Chip>(position)?.let {
//                    Toast.makeText(context, "Выбран ${it.text}", Toast.LENGTH_SHORT).show()
//                }
//            }

//            chipClose.setOnCloseIconClickListener {
//                Toast.makeText(
//                    context,
//                    "Close is Clicked",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }
    }
}
