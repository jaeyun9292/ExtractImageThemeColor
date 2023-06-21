package com.example.extractimagethemecolor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.example.extractimagethemecolor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val screenManager = ScreenManager(this)

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val images = listOf(
        R.drawable.test_0, R.drawable.test_1, R.drawable.test_2, R.drawable.test_3, R.drawable.test_4,
        R.drawable.test_5, R.drawable.test_6, R.drawable.test_7, R.drawable.test_8, R.drawable.test_9,
    )
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setImage(count)

        Handler().postDelayed({
            screenManager.setFullScreen()
        }, 500)

        binding.textColor01.setOnClickListener {
            setImage(++count % images.size)
        }
    }

    private fun setImage(count: Int) {
        Log.e(TAG, "setImage: $count")
        val imageResource = applicationContext.resources.getIdentifier("test_${count}", "drawable", applicationContext.packageName)

//        binding.imageView01.setImageResource(images[count])
//        val palette = createPaletteSync(BitmapFactory.decodeResource(resources, images[count]))

        binding.imageView01.setImageResource(imageResource)
        val palette = createPaletteSync(BitmapFactory.decodeResource(resources, imageResource))

        setColor(binding.mainColorBorder, palette.vibrantSwatch)
        setColor(binding.textColor01, palette.lightVibrantSwatch)
        setColor(binding.textColor02, palette.vibrantSwatch)
        setColor(binding.textColor03, palette.darkVibrantSwatch)
        setColor(binding.textColor04, palette.lightMutedSwatch)
        setColor(binding.textColor05, palette.mutedSwatch)
        setColor(binding.textColor06, palette.darkMutedSwatch)
    }

    private fun setColor(layout: ConstraintLayout, swatch: Palette.Swatch?) {
        with(layout) {
            Log.e(TAG, "setColor: ${Integer.toHexString(swatch?.rgb ?: 0)}")
            setBackgroundColor(swatch?.rgb ?: ContextCompat.getColor(context, R.color.default_background))
        }
    }

    private fun setColor(view: TextView, swatch: Palette.Swatch?) {
        with(view) {
            Log.e(TAG, "setColor: ${Integer.toHexString(swatch?.rgb ?: 0)}")
            setBackgroundColor(swatch?.rgb ?: ContextCompat.getColor(context, R.color.default_background))
            setTextColor(swatch?.titleTextColor ?: ContextCompat.getColor(context, R.color.default_textColor))
        }
    }

    // 동기
    private fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

    // 비동기
    fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->

        }
    }
}