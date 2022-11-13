package scientists.house.affiche

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import scientists.house.affiche.app.utils.image_loader.GlideLoaderCreator
import scientists.house.affiche.app.utils.image_loader.ImageLoader

/**
 * Entry point of the app should be annotated with [HiltAndroidApp].
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ImageLoader.loaderCreator = GlideLoaderCreator()
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}
