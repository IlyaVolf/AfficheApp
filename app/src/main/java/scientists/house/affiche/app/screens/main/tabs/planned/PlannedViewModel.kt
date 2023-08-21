package scientists.house.affiche.app.screens.main.tabs.planned

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import scientists.house.affiche.app.model.planned.PlannedRepository
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.logger.Logger

@HiltViewModel
class PlannedViewModel @Inject constructor(
    private val plannedRepository: PlannedRepository,
    logger: Logger
) : BaseViewModel(logger)
