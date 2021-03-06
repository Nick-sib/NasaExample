package geekbarains.material.view.ui.adapters.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.R
import geekbarains.material.model.entity.*
import geekbarains.material.view.ui.adapters.recyclerview.adapter.holders.*
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.IDataInteract
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.ItemTouchHelperAdapter
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.OnStartDragListener

class PlanetsRecyclerviewAdapter(
        private var data: MutableList<Pair<DataPlanet, Boolean>>,
        private val doShowText: ((text: String) -> Unit),
        private val dragListener: OnStartDragListener
): RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    private val dataInteract = object : IDataInteract {
        override fun addItem(position: Int) {
            data.add(
                position,
                Pair(DataPlanet(type = PLANET_TYPE_MARS), false)
            )
            notifyItemInserted(position)
        }

        override fun removeItem(position: Int) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }

        override fun moveUp(position: Int) {
            if (position > 1 ) {
                data.removeAt(position).apply {
                    data.add(position - 1, this)
                }
            notifyItemMoved(position, position - 1)
            } else {
                doShowText("Извините, выше не движется")
            }
        }

        override fun moveDown(position: Int) {
            if (position < data.size - 1) {
                data.removeAt(position).apply {
                    data.add(position + 1, this)
                }
                notifyItemMoved(position, position + 1)
            } else {
                doShowText("Извините, ниже не движется")
            }
        }
    }


    fun setData(newItems: List<Pair<DataPlanet, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(data, newItems))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newItems)
    }

    fun addItem(dataPlanet: DataPlanet){
        data.add(Pair(dataPlanet, false))
        notifyItemInserted(data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PLANET_TYPE_EARTH ->
                EarthViewHolder(
                    inflater.inflate(R.layout.recycler_item_earth, parent, false) as View,
                    doShowText
                )
            PLANET_TYPE_MARS ->
                MarsViewHolder(
                    inflater.inflate(R.layout.recycler_item_mars, parent, false) as View,
                    dragListener,
                    dataInteract
                )
            PLANET_TYPE_JUPITER ->
                JupiterViewHolder(
                    inflater.inflate(R.layout.recycler_item_jupaiter, parent, false) as View,
                    {toggleText(it)},
                    dragListener
                )
            else -> HeaderViewHolder(
                    inflater.inflate(R.layout.recycler_item_header, parent, false) as View,
                    doShowText
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) PLANET_TYPE_HEADER else data[position].first.type

    override fun getItemCount(): Int = data.size


    private fun toggleText(position: Int){
        data[position] = data[position].let {
            it.first to !it.second
        }
        notifyItemChanged(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (toPosition > 0) {
            data.removeAt(fromPosition).apply {
                data.add(toPosition, this)
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}