package scientists.house.affiche.app.model.news

import scientists.house.affiche.sources.news.entities.NewsPostDataEntity
import scientists.house.affiche.sources.news.entities.NewsDetailedPostDataEntity

interface NewsSource {

    suspend fun getNewsPosts(): List<NewsPostDataEntity>

    suspend fun getDetailedNewsPost(link: String): NewsDetailedPostDataEntity
}
