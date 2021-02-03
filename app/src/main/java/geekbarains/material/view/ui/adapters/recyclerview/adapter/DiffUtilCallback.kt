package geekbarains.material.view.ui.adapters.recyclerview.adapter

import androidx.recyclerview.widget.DiffUtil
import geekbarains.material.model.entity.DataPlanet

class DiffUtilCallback(
        private var oldItems: List<Pair<DataPlanet, Boolean>>,
        private var newItems: List<Pair<DataPlanet, Boolean>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].first.someText == newItems[newItemPosition].first.someText

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return Change(
                oldItem,
                newItem
        )
    }
}

