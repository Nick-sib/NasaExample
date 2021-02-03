package geekbarains.material.view.ui.adapters.recyclerview.adapter.holders

import android.view.View
import geekbarains.material.model.entity.DataPlanet

class HeaderViewHolder(
        view: View,
        private val onClickView: ((text: String) -> Unit)?
) : BaseViewHolder(view) {

    override fun bind(dataItem: Pair<DataPlanet, Boolean>) {
        onClickView?.also { doClick ->
            itemView.setOnClickListener {
                doClick("HEADER")
            }
        }
    }
}