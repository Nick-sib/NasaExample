package geekbarains.material.view.ui.fragmetns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import geekbarains.material.databinding.FragmentChipsBinding



class ChipsFragment : Fragment() {

    private var binding: FragmentChipsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentChipsBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run{
            chipGroup.setOnCheckedChangeListener { chipGroup, position ->
                chipGroup.findViewById<Chip>(position)?.let {
                    Toast.makeText(context, "Выбран ${it.text}", Toast.LENGTH_SHORT).show()
                }
            }

            chipClose.setOnCloseIconClickListener {
                Toast.makeText(
                    context,
                    "Close is Clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
