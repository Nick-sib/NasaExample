package geekbarains.material.model.entity

interface LoadedData {
    fun getCount(): Int
    fun getTitle(position: Int): String
    fun saveData(position: Int, value: PictureOfTheDayData.Success)
    fun loadData(position: Int): PictureOfTheDayData.Success?
}