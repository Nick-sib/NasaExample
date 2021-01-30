package geekbarains.material.model.entity

const val PLANET_TYPE_HEADER = 0
const val PLANET_TYPE_MARS = 1
const val PLANET_TYPE_EARTH = 2
const val PLANET_TYPE_JUPITER = 3

data class DataPlanet(
    val id: Int = 0,
    val someText: String = "Text",
    val someDescription: String? = "Description",
    val type: Int = PLANET_TYPE_HEADER
)