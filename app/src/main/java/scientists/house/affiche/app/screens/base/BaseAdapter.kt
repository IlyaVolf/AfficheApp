package scientists.house.affiche.app.screens.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
* Базовый класс адаптера основанного на view binding
*
* @property onItemClickListener Функция обратного вызова для обработки нажатий на элемент списка
*/
abstract class BaseAdapter<K : BaseViewHolder<T>, T>(
    var onItemClickListener: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<K>() {

    /**
     * Список элементов
     */
    var items: ArrayList<T> = arrayListOf()

    /**
     * Получить элемент по индексу
     */
    fun getItem(position: Int): T {
        return items[position]
    }

    /**
     * Получить количество элементов списка
     */
    override fun getItemCount(): Int = items.count()

    /**
     * Изменить текущий список элементов на новый
     *
     * @param items Новый список элементов
     */
    open fun setupItems(items: List<T>) {
        this.items = arrayListOf()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    open fun update(items: List<T>, callback: (Boolean) -> Unit) {}

    /**
     * Создать view holder
     *
     * @param parent Родительская view group
     */
    abstract fun takeViewHolder(parent: ViewGroup): BaseViewHolder<T>

    /**
     * Связать view holder с данными
     *
     * @param holder [BaseViewHolder]
     * @param position Позиция элемента списка
     */
    override fun onBindViewHolder(holder: K, position: Int) {
        bindHolder(holder, position)
    }

    /**
     * Вызываем в onBindViewHolder
     *
     * @param holder [BaseViewHolder]
     * @param position Позиция элемента списка
     */
    open fun bindHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = this.items[position]
        holder.bindItem(item)
        holder.tag = position
        onItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.invoke(position)
            }
        }
    }
}
