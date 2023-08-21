package scientists.house.affiche.app.screens.main.tabs.affiche

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scientists.house.affiche.app.utils.DataHolder
import scientists.house.affiche.app.model.affiche.AfficheRepository
import scientists.house.affiche.app.model.affiche.entities.AfficheDetailedPost
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.ObservableHolder
import scientists.house.affiche.app.utils.logger.Logger
import scientists.house.affiche.app.utils.share

class AffichePostDetailsViewModel @AssistedInject constructor(
    @Assisted link: String,
    private val afficheRepository: AfficheRepository,
    logger: Logger
) : BaseViewModel(logger) {

    private val _afficheDetailedPost = ObservableHolder<AfficheDetailedPost>(DataHolder.loading())
    val afficheDetailedPost = _afficheDetailedPost.share()

    init {
        load(link)
    }

    fun load(link: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = afficheRepository.getDetailedAffiche(link)
            withContext(Dispatchers.Main) {
                _afficheDetailedPost.value = DataHolder.ready(data)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _afficheDetailedPost.value = DataHolder.error(e)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(link: String): AffichePostDetailsViewModel
    }
}
