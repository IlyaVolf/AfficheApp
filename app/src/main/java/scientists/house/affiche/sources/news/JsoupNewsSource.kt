package scientists.house.affiche.sources.news

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.news.NewsSource
import scientists.house.affiche.app.model.news.entities.NewsItem

@Singleton
class JsoupNewsSource @Inject constructor() : NewsSource {

    override suspend fun getNews(): List<NewsItem> {
        TODO("Not yet implemented")
    }
}
