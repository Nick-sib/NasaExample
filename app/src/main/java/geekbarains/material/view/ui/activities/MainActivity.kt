package geekbarains.material.view.ui.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import geekbarains.material.App
import geekbarains.material.R
import geekbarains.material.STYLE_DEFAULT
import geekbarains.material.STYLE_TOXIC
import geekbarains.material.databinding.MainActivityBinding

import geekbarains.material.view.ui.fragmetns.PictureOfTheDayFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(
                when (App.instance.selectedStyle){
                    STYLE_DEFAULT -> R.style.AppTheme
                    STYLE_TOXIC -> R.style.ToxicTheme
                    else -> R.style.AppTheme
                } )
        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.instance())
                .commitNow()
        }
    }
}
