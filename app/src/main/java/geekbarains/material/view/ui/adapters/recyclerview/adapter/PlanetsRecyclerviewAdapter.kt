package geekbarains.material.view.ui.adapters.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.R
import geekbarains.material.model.entity.*

class PlanetsRecyclerviewAdapter(
        private var data: MutableList<Pair<DataPlanet, Boolean>>,
): RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PLANET_TYPE_EARTH ->
                EarthViewHolder(
                    inflater.inflate(R.layout.recycler_item_earth, parent, false) as View
                )
            PLANET_TYPE_MARS ->
                MarsViewHolder(
                    inflater.inflate(R.layout.recycler_item_mars, parent, false) as View
                )
            PLANET_TYPE_JUPITER ->
                JupiterViewHolder(
                        inflater.inflate(R.layout.recycler_item_jupaiter, parent, false) as View
                )
            else -> HeaderViewHolder(
                    inflater.inflate(R.layout.recycler_item_header, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) PLANET_TYPE_HEADER else data[position].first.type

    override fun getItemCount(): Int = data.size

}