package geekbarains.material

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

const val DESCRIPTION_LENGTH = 250
val EARTH_TITLE = arrayOf(
        "Tokë","Earth","Lurra","Зямля","Zemlja","Föld","Terra","γη","ערד","Jörðin","žemė","Земља","지구")
val EARTH_DESCRIPTION = arrayOf(
        "В смысле грунт и почва","Как суша в противоположность морю","Одна из мировых стихий в алхимии",
        "Сыпучие и глинистые горные породы","Земельный участок, территория","Земля — экономический ресурс")

@SuppressLint("ConstantLocale")
val locate: Locale = Locale.getDefault()
val formatter = SimpleDateFormat("yyyy-MM-dd", locate)


fun Int.getData(): String =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    LocalDate.now().plusDays(this.toLong()).toString()
} else {
    val date = Calendar.getInstance(locate).let {
        it.add(Calendar.DATE, this)
        it.time
    }
    formatter.format(date)
}

fun getEarthTitle() = EARTH_TITLE.random()
fun getEarthDescription() = EARTH_DESCRIPTION.random()