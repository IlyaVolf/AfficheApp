package scientists.house.affiche.app.utils.image_loader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.File

/**
 * Загрузка изображений с помощью @Glide
 */
class GlideImageLoader constructor(context: Context) : ImageLoader {

    private var requestManager: RequestManager = Glide.with(context)
    private lateinit var requestBuilder: RequestBuilder<Drawable>
    private var requestOptions: RequestOptions = RequestOptions()
    private var transformations = mutableListOf<BitmapTransformation>()

    override fun centerCrop(): ImageLoader {
        transformations.add(CenterCrop())
        return this
    }

    override fun roundedCorners(roundingRadius: Int): ImageLoader {
        transformations.add(RoundedCorners(roundingRadius))
        return this
    }

    override fun circleCrop(): ImageLoader {
        transformations.add(CircleCrop())
        return this
    }

    override fun centerInside(): ImageLoader {
        transformations.add(CenterInside())
        return this
    }

    override fun placeholder(placeholderDrawable: Drawable): ImageLoader {
        requestOptions.placeholder(placeholderDrawable)
        return this
    }

    override fun error(errorResId: Int): ImageLoader {
        requestOptions.error(errorResId)
        return this
    }

    override fun error(errorDrawable: Drawable): ImageLoader {
        requestOptions.error(errorDrawable)
        return this
    }

    override fun placeholder(placeholderResId: Int): ImageLoader {
        requestOptions.placeholder(placeholderResId)
        return this
    }

    override fun resize(targetWidth: Int, targetHeight: Int): ImageLoader {
        requestOptions.override(targetWidth, targetHeight)
        return this
    }

    override fun into(imageView: ImageView, callback: ImageLoaderListener?) {
        requestOptions.transform(*transformations.toTypedArray())
        requestBuilder
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.onError(e.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    callback?.onSuccess()
                    return false
                }
            })
            .apply(requestOptions)
            .into(imageView)
    }

    override fun load(url: String?): ImageLoader {
        requestBuilder = requestManager.load(url)
        return this
    }

    override fun load(file: File): ImageLoader {
        requestBuilder = requestManager.load(file)
        return this
    }

    override fun load(@DrawableRes resourceId: Int): ImageLoader {
        requestBuilder = requestManager.load(resourceId)
        return this
    }
}

/**
 * Создает экземпляр загрузчика @Glide
 */
class GlideLoaderCreator : LoaderCreator {
    override fun getInstance(context: Context): ImageLoader {
        return GlideImageLoader(context)
    }
}