package geekbarains.material.view.ui.adapters.recyclerview.adapter.holders


import android.view.View
import android.widget.TextView
import geekbarains.material.R
import geekbarains.material.model.entity.DataPlanet

class MarsViewHolder(view: View) : BaseViewHolder(view) {

    override fun bind(dataItem: Pair<DataPlanet, Boolean>) {
//        itemView.marsImageView.setOnClickListener { onListItemClickListener.onItemClick(dataItem.first) }
//        itemView.addItemImageView.setOnClickListener { addItem() }
//        itemView.removeItemImageView.setOnClickListener { removeItem() }
//        itemView.moveItemDown.setOnClickListener { moveDown() }
//        itemView.moveItemUp.setOnClickListener { moveUp() }
        itemView.findViewById<TextView>(R.id.tv_description)?.visibility =
            if (dataItem.second) View.VISIBLE else View.GONE
//        itemView.marsTextView.setOnClickListener { toggleText() }
//        itemView.dragHandleImageView.setOnTouchListener { _, event ->
//            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                dragListener.onStartDrag(this)
//            }
//            false
//        }
    }

//    private fun addItem() {
//        data.add(layoutPosition, generateItem())
//        notifyItemInserted(layoutPosition)
//    }
//
//    private fun removeItem() {
//        data.removeAt(layoutPosition)
//        notifyItemRemoved(layoutPosition)
//    }
//
//    private fun moveUp() {
//        layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
//            data.removeAt(currentPosition).apply {
//                data.add(currentPosition - 1, this)
//            }
//            notifyItemMoved(currentPosition, currentPosition - 1)
//        }
//    }
//
//    private fun moveDown() {
//        layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
//            data.removeAt(currentPosition).apply {
//                data.add(currentPosition + 1, this)
//            }
//            notifyItemMoved(currentPosition, currentPosition + 1)
//        }
//    }
//
//    private fun toggleText() {
//        data[layoutPosition] = data[layoutPosition].let {
//            it.first to !it.second
//        }
//        notifyItemChanged(layoutPosition)
//    }
//
//    override fun onItemSelected() {
//        itemView.setBackgroundColor(Color.LTGRAY)
//    }
//
//    override fun onItemClear() {
//        itemView.setBackgroundColor(Color.WHITE)
//    }


}