package scientists.house.affiche.app.screens.main.tabs.affiche

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scientists.house.affiche.app.utils.DataHolder
import scientists.house.affiche.app.model.affiche.AfficheRepository
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.ObservableHolder
import scientists.house.affiche.app.utils.logger.Logger
import scientists.house.affiche.app.utils.share

@HiltViewModel
class AfficheViewModel @Inject constructor(
    private val afficheRepository: AfficheRepository,
    logger: Logger
) : BaseViewModel(logger) {

    private val _affichePosts = ObservableHolder<List<AffichePost>>(DataHolder.loading())
    val affichePosts = _affichePosts.share()

    init {
        load()
    }

    fun load() = viewModelScope.launch(IO) {
        try {
            val data = afficheRepository.getAffichePosts()
            withContext(Dispatchers.Main) {
                _affichePosts.value = DataHolder.ready(data)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _affichePosts.value = DataHolder.error(e)
            }
        }
    }
}
