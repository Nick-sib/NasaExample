package geekbarains.material.model.entity

object LoadedDataImpl: LoadedData {
    private val titles = arrayOf("Позавчера", "Вчера", "Сегодня")
    private val savedData = Array<PictureOfTheDayData.Success?>(titles.size) {null}

    override fun getCount(): Int = titles.size

    override fun getTitle(position: Int): String = titles[position]

    override fun saveData(position: Int, value: PictureOfTheDayData.Success) {
        savedData[position] = value
    }

    override fun loadData(position: Int): PictureOfTheDayData.Success? = savedData[position]


}