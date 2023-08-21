package scientists.house.affiche.app.screens.base

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Базовый класс ViewHolder
 *
 * @property itemViewBinding байндинг
 * @property tag номер (для определения индекса элемента)
 * @property context [Context]
 * @property resources ресурсы приложения
 * */
abstract class BaseViewHolder<T>(val itemViewBinding: ViewBinding) :
    RecyclerView.ViewHolder(itemViewBinding.root) {

    /**
     * Наполнение viewholder
     * */
    abstract fun bindItem(item: T)

    open var tag: Int = 0

    val context: Context
        get() = itemViewBinding.root.context

    val resources: Resources
        get() = itemViewBinding.root.resources

    /**
     * Получить строку из ресурса
     *
     * @param stringRes Ресурс строки
     */
    protected fun getString(
        @StringRes stringRes: Int
    ): String = resources.getString(stringRes)
}
