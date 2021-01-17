package geekbarains.material

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

const val THEME_LIGHT = 0
const val THEME_DARK  = 1
const val THEME_TOXIC = 2
private const val PREFS_KEY_THEME = "theme"

class App: Application(){

    private fun saveKey(key: String, theme: Int) = sharedPrefs.edit().putInt(key, theme).apply()
    private fun loadKey(key: String) = sharedPrefs.getInt(key, THEME_LIGHT)

    private val sharedPrefs by lazy {
        getSharedPreferences(
                (App::class).qualifiedName,
                Context.MODE_PRIVATE
        )
    }

    private fun setTheme(themeMode: Int, prefsMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        saveKey(PREFS_KEY_THEME, prefsMode)
    }

    var selectedTheme = 0
        set(value) {
            val themeMode = if (value == 1) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            setTheme(themeMode, value)
            field = value
        }

    override fun onCreate() {
        super.onCreate()
        instance = this
        selectedTheme = loadKey(PREFS_KEY_THEME)

        if (selectedTheme == THEME_DARK)
            setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK)
    }

    companion object {
        lateinit var instance: App
            private set
    }

}