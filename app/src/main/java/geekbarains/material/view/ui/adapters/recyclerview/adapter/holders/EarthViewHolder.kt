package geekbarains.material.view.ui.adapters.recyclerview.adapter.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import geekbarains.material.R
import geekbarains.material.model.entity.DataPlanet

class EarthViewHolder(
        view: View,
        private val onClickView: ((text: String) -> Unit)?
) : BaseViewHolder(view) {

    override fun bind(dataItem: Pair<DataPlanet, Boolean>) {
        itemView.findViewById<TextView>(R.id.tv_text)?.text = dataItem.first.someText
        itemView.findViewById<TextView>(R.id.tv_description)?.text = dataItem.first.someDescription

        onClickView?.also { doClick->
            itemView.findViewById<ImageView>(R.id.iv_const_earth)?.setOnClickListener {
                doClick(dataItem.first.someDescription)
            }
        }
    }

}