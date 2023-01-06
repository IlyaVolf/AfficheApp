package scientists.house.affiche.sources.news

import scientists.house.affiche.sources.news.entities.NewsPost

interface NewsApi {
    suspend fun getNews(): List<NewsPost>
}
