package scientists.house.affiche.app.utils.image_loader

import android.content.Context

/**
 * Создание экземпляра загрузчика
 */
interface LoaderCreator {
    fun getInstance(context: Context): ImageLoader
}