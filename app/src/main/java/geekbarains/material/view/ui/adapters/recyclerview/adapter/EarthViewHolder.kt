package geekbarains.material.view.ui.adapters.recyclerview.adapter

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.R
import geekbarains.material.model.entity.DataPlanet

class EarthViewHolder(view: View) : BaseViewHolder(view) {

    override fun bind(dataItem: Pair<DataPlanet, Boolean>) {
        itemView.findViewById<TextView>(R.id.tv_text)?.text = dataItem.first.someText
        itemView.findViewById<TextView>(R.id.tv_description)?.text = dataItem.first.someDescription
//        itemView.findViewById<ImageView>(R.id.iv_const_earth).setOnClickListener {
//
//        }
    }

//    override fun bind(dataItem: DataPlanet) {
//        if (layoutPosition != RecyclerView.NO_POSITION) {
//            itemView.descriptionTextView.text = dataItem.first.someDescription
//            itemView.wikiImageView.setOnClickListener {
//                onListItemClickListener.onItemClick(
//                        dataItem.first
//                )
//            }
//        }
//    }
}