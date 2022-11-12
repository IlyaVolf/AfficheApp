package scientists.house.affiche.sources.news

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.news.NewsSource
import scientists.house.affiche.app.model.news.entities.NewsItem
import scientists.house.affiche.sources.base.BaseRetrofitSource
import scientists.house.affiche.sources.base.RetrofitConfig

@Singleton
class RetrofitNewsSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), NewsSource {

    private val newsApi = retrofit.create(NewsApi::class.java)

    override suspend fun getNews(): List<NewsItem> = wrapRetrofitExceptions {
        TODO("Not yet implemented")
    }
}
