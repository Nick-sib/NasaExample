package geekbarains.material

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

const val THEME_LIGHT = AppCompatDelegate.MODE_NIGHT_NO
const val THEME_DARK  = AppCompatDelegate.MODE_NIGHT_YES
const val THEME_SYSTEM = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

const val STYLE_DEFAULT = 0
const val STYLE_TOXIC = 1

private const val PREFS_KEY_THEME = "theme"
private const val PREFS_KEY_STYLE = "style"


class App: Application(){

    private fun saveKey(key: String, theme: Int) = sharedPrefs.edit().putInt(key, theme).apply()
    private fun loadKey(key: String, defValue: Int) = sharedPrefs.getInt(key, defValue)

    private val sharedPrefs by lazy {
        getSharedPreferences(
                (App::class).qualifiedName,
                Context.MODE_PRIVATE
        )
    }

    private fun loadTheme() = loadKey(PREFS_KEY_THEME, THEME_SYSTEM)
    private fun saveTheme(value: Int) = saveKey(PREFS_KEY_THEME, value)

    private fun loadStyle() = loadKey(PREFS_KEY_STYLE, STYLE_DEFAULT)
    private fun saveStyle(value: Int) = saveKey(PREFS_KEY_THEME, value)


//    private fun setTheme(themeMode: Int, prefsMode: Int) {
//        AppCompatDelegate.setDefaultNightMode(themeMode)
//        saveKey(PREFS_KEY_THEME, prefsMode)
//    }

    var selectedTheme: Int = THEME_SYSTEM
        set(value) {
            if (value != field) {
                saveTheme(value)
                AppCompatDelegate.setDefaultNightMode(value)
            }
            field = value
        }

    var selectedStyle: Int = STYLE_DEFAULT
        set(value) {
            if (value != field) {
                saveStyle(value)
            }
            field = value
        }



    override fun onCreate() {
        super.onCreate()
        instance = this
        selectedTheme = loadTheme()
        selectedStyle = loadStyle()
    }

    companion object {
        lateinit var instance: App
            private set
    }

}