package geekbarains.material.view.ui.adapters.recyclerview.adapter.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.model.entity.DataPlanet

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(dataItem: Pair<DataPlanet, Boolean>)
}