package scientists.house.affiche.app.screens.main.tabs.news

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import scientists.house.affiche.app.model.news.NewsRepository
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.logger.Logger

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    logger: Logger
) : BaseViewModel(logger)
