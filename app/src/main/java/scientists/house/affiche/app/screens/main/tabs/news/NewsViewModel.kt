package scientists.house.affiche.app.screens.main.tabs.news

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scientists.house.affiche.app.model.DataHolder
import scientists.house.affiche.app.model.news.NewsRepository
import scientists.house.affiche.app.model.news.entities.NewsPost
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.ObservableHolder
import scientists.house.affiche.app.utils.logger.Logger
import scientists.house.affiche.app.utils.share

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    logger: Logger
) : BaseViewModel(logger) {

    private val _newsPosts = ObservableHolder<List<NewsPost>>(DataHolder.loading())
    val newsPosts = _newsPosts.share()

    init {
        load()
    }

    fun load() = viewModelScope.launch(IO) {
        withContext(Dispatchers.Main) {
            _newsPosts.value = DataHolder.loading()
        }
        try {
            val data = newsRepository.getNewsPosts()
            withContext(Dispatchers.Main) {
                _newsPosts.value = DataHolder.ready(data)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _newsPosts.value = DataHolder.error(e)
            }
        }
    }

}
