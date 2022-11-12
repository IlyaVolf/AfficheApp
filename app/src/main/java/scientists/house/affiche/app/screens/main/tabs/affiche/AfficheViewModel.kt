package scientists.house.affiche.app.screens.main.tabs.affiche

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import scientists.house.affiche.app.model.affiche.AfficheRepository
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.logger.Logger

@HiltViewModel
class AfficheViewModel @Inject constructor(
    private val afficheRepository: AfficheRepository,
    logger: Logger
) : BaseViewModel(logger) {

    fun reload() = viewModelScope.launch {
        // reload
    }
}
