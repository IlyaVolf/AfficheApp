package scientists.house.affiche.sources.news

import scientists.house.affiche.sources.news.entities.NewsPostDataEntity

interface NewsApi {
    suspend fun getNews(): List<NewsPostDataEntity>
}
