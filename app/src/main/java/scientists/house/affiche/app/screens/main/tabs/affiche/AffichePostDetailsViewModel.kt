package scientists.house.affiche.app.screens.main.tabs.affiche

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.logger.Logger

@HiltViewModel
class AffichePostDetailsViewModel @Inject constructor(
    logger: Logger
) : BaseViewModel(logger)
