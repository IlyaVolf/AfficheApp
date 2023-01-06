package scientists.house.affiche.app.screens.main.tabs.news

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scientists.house.affiche.app.model.DataHolder
import scientists.house.affiche.app.model.news.entities.NewsDetailedPost
import scientists.house.affiche.app.model.news.NewsRepository
import scientists.house.affiche.app.screens.base.BaseViewModel
import scientists.house.affiche.app.utils.ObservableHolder
import scientists.house.affiche.app.utils.logger.Logger
import scientists.house.affiche.app.utils.share

class NewsPostDetailsViewModel @AssistedInject constructor(
    @Assisted link: String,
    private val newsRepository: NewsRepository,
    logger: Logger
) : BaseViewModel(logger) {

    private val _newsDetailedPost = ObservableHolder<NewsDetailedPost>(DataHolder.loading())
    val newsDetailedPost = _newsDetailedPost.share()

    init {
        load(link)
    }

    fun load(link: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = newsRepository.getDetailedNews(link)
            withContext(Dispatchers.Main) {
                _newsDetailedPost.value = DataHolder.ready(data)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _newsDetailedPost.value = DataHolder.error(e)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(link: String): NewsPostDetailsViewModel
    }
}
