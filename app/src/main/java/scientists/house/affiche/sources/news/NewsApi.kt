package scientists.house.affiche.sources.news

import scientists.house.affiche.sources.news.entities.NewsItem

interface NewsApi {
    suspend fun getNews(): List<NewsItem>
}
