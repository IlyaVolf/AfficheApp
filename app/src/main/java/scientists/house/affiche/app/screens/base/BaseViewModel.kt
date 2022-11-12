package scientists.house.affiche.app.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import scientists.house.affiche.app.R
import scientists.house.affiche.app.model.BackendException
import scientists.house.affiche.app.model.ConnectionException
import scientists.house.affiche.app.utils.MutableLiveEvent
import scientists.house.affiche.app.utils.logger.Logger
import scientists.house.affiche.app.utils.publishEvent
import scientists.house.affiche.app.utils.share

open class BaseViewModel(
    val logger: Logger
) : ViewModel() {

    private val _showErrorMessageResEvent = MutableLiveEvent<Int>()
    val showErrorMessageResEvent = _showErrorMessageResEvent.share()

    private val _showErrorMessageEvent = MutableLiveEvent<String>()
    val showErrorMessageEvent = _showErrorMessageEvent.share()

    fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: ConnectionException) {
                logError(e)
                _showErrorMessageResEvent.publishEvent(R.string.network_connection_error)
            } catch (e: BackendException) {
                logError(e)
                _showErrorMessageEvent.publishEvent(e.message ?: "")
            } catch (e: Exception) {
                logError(e)
                _showErrorMessageResEvent.publishEvent(R.string.network_internal_error)
            }
        }
    }

    fun logError(e: Throwable) {
        logger.error(javaClass.simpleName, e)
    }
}
