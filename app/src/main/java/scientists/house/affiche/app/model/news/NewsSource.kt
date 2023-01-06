package scientists.house.affiche.app.model.news

import scientists.house.affiche.sources.news.entities.NewsPost
import scientists.house.affiche.sources.news.entities.NewsDetailedPost

interface NewsSource {

    suspend fun getNewsPosts(): List<NewsPost>

    suspend fun getDetailedNewsPost(link: String): NewsDetailedPost
}
