package geekbarains.material.view.ui.adapters.recyclerview.adapter.holders


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import geekbarains.material.R
import geekbarains.material.model.entity.DataPlanet
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.IDataInteract
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.ItemTouchHelperViewHolder
import geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces.OnStartDragListener

class MarsViewHolder(
    view: View,
    private val dragListener: OnStartDragListener,
    private val dataInteract: IDataInteract
) : BaseViewHolder(view), ItemTouchHelperViewHolder {

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(dataItem: Pair<DataPlanet, Boolean>) {
        itemView.findViewById<ImageView>(R.id.iv_add).setOnClickListener {
            dataInteract.addItem(layoutPosition)
        }
        itemView.findViewById<ImageView>(R.id.iv_remove).setOnClickListener {
            dataInteract.removeItem(layoutPosition)
        }
        itemView.findViewById<ImageView>(R.id.iv_move_down).setOnClickListener {
            dataInteract.moveDown(layoutPosition)
        }
        itemView.findViewById<ImageView>(R.id.iv_move_up).setOnClickListener {
            dataInteract.moveUp(layoutPosition)
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