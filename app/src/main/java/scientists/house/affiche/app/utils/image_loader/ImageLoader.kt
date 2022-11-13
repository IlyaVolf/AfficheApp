package scientists.house.affiche.app.utils.image_loader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import java.io.File
import scientists.house.affiche.App

/**
 * Класс для загрузки изображений
 */
interface ImageLoader {
    fun centerCrop(): ImageLoader

    fun roundedCorners(roundingRadius: Int): ImageLoader

    fun circleCrop(): ImageLoader

    fun centerInside(): ImageLoader

    fun placeholder(placeholderDrawable: Drawable): ImageLoader

    fun error(errorResId: Int): ImageLoader

    fun error(errorDrawable: Drawable): ImageLoader

    fun placeholder(placeholderResId: Int): ImageLoader

    fun resize(targetWidth: Int, targetHeight: Int): ImageLoader

    fun into(imageView: ImageView, callback: ImageLoaderListener? = null)

    fun load(url: String?): ImageLoader

    fun load(file: File): ImageLoader

    fun load(@DrawableRes resourceId: Int): ImageLoader

    companion object {
        lateinit var loaderCreator: LoaderCreator

        fun load(url: String?): ImageLoader {
            return loaderCreator.getInstance(App.context).load(url)
        }

        fun load(file: File): ImageLoader {
            return loaderCreator.getInstance(App.context).load(file)
        }

        fun load(@DrawableRes resourceId: Int): ImageLoader {
            return loaderCreator.getInstance(App.context).load(resourceId)
        }
    }
}