package scientists.house.affiche.app.screens.main.tabs.planned

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import scientists.house.affiche.app.model.planned.PlannedRepository
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.logger.Logger

@HiltViewModel
class PlannedViewModel @Inject constructor(
    private val plannedRepository: PlannedRepository,
    logger: Logger
) : BaseViewModel(logger) {

    fun reload() = viewModelScope.launch {
        // reload
    }
}
