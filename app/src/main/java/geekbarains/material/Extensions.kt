package geekbarains.material

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

const val DESCRIPTION_LENGTH = 250

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
