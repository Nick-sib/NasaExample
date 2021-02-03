package geekbarains.material.view.ui.adapters.recyclerview.adapter.interfaces

interface IDataInteract {
    fun addItem(position: Int)
    fun removeItem(position: Int)
    fun moveUp(position: Int)
    fun moveDown(position: Int)
}