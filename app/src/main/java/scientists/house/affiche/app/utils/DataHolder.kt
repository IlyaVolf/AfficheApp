package scientists.house.affiche.app.utils

import kotlin.Result

/**
 * Класс для хранения информации о статусе состояния данных
 */
sealed class DataHolder<out TData> {

    /**
     * Состояние загрузки данных
     */
    object LOADING : DataHolder<Nothing>()

    /**
     * Состояние готовности данных
     *
     * @property data Загруженные данные
     */
    class READY<out TData>(val data: TData) : DataHolder<TData>()

    /**
     * Состояние ошибки загрузки данных
     *
     * @property failure Информация об ошибке
     */
    class ERROR(val failure: Throwable) : DataHolder<Nothing>()

    /**
     * @param block Будет вызван в случае готовности данных
     */
    inline fun onReady(block: (TData) -> Unit) {
        if (this is READY) {
            block(this.data)
        }
    }

    /**
     * @param block Будет вызван в случае неготовности данных
     */
    inline fun onNotReady(block: () -> Unit) {
        if (this !is READY) {
            block()
        }
    }

    /**
     * @param block Будет вызван в состоянии загрузки данных
     */
    inline fun onLoading(block: () -> Unit) {
        if (this is LOADING) {
            block()
        }
    }

    /**
     * @param block Будет вызван в состоянии ошибки загрузки данных
     */
    inline fun onError(block: (Throwable) -> Unit) {
        if (this is ERROR) {
            block(failure)
        }
    }

    /**
     * Конвертирует состяние в новый тип данных в зависимости от текущего статуса
     */
    inline fun <TResult> fold(
        onReady: (TData) -> TResult,
        onError: (Throwable) -> TResult,
        onLoading: () -> TResult
    ): TResult {
        return when (this) {
            is READY -> onReady(data)
            is ERROR -> onError(failure)
            LOADING -> onLoading()
        }
    }

    /**
     * Проверка на состояние загрузки
     */
    inline val isLoading get() = this is LOADING

    /**
     * Проверка на состояние готовности
     */
    inline val isReady get() = this is READY

    /**
     * Проверка на состояние ошибки
     */
    inline val isError get() = this is ERROR

    /**
     *
     */
    companion object {
        /**
         * Создает [DataHolder] в состоянии загрузки данных
         */
        fun loading() = LOADING

        /**
         * Создает [DataHolder] в состоянии готовности данных
         */
        fun <TData> ready(data: TData) = READY(data)

        /**
         * Создает [DataHolder] в состоянии ошибки загрузки данных
         */
        fun error(failure: Throwable) = ERROR(failure)
    }
}

/**
 * Создает [DataHolder] из [kotlin.Result]
 */
fun <T> Result<T>.toDataHolder(): DataHolder<T> = fold(
    onSuccess = { DataHolder.ready(it) },
    onFailure = { DataHolder.error(it) }
)
