package geekbarains.material.view.ui.adapters.recyclerview.adapter.holders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import geekbarains.material.R
import geekbarains.material.model.entity.DataPlanet
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.ItemTouchHelperViewHolder
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.OnStartDragListener

class JupiterViewHolder(
    view: View,
    private val toggleText: ((position: Int) -> Unit)?,
    private val dragListener: OnStartDragListener
) : BaseViewHolder(view), ItemTouchHelperViewHolder {

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(dataItem: Pair<DataPlanet, Boolean>) {
        itemView.findViewById<TextView>(R.id.tv_description).visibility =
            if (dataItem.second) View.VISIBLE else View.GONE

        toggleText?.also { doToggle->
            itemView.findViewById<TextView>(R.id.tv_const_text)?.setOnClickListener {
                doToggle(layoutPosition)
            }
        }

        itemView.findViewById<ImageView>(R.id.iv_drag_handle).setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                dragListener.onStartDrag(this)
            }
            false
        }

    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(android.R.attr.background)
    }
}