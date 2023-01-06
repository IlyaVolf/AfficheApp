package scientists.house.affiche.app.model.news

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.news.entities.NewsDetailedPost
import scientists.house.affiche.app.model.news.entities.NewsPost
import scientists.house.affiche.app.model.news.entities.toUiNewsDetailedPosts
import scientists.house.affiche.app.model.news.entities.toUiNewsPosts

@Singleton
class NewsRepository @Inject constructor(
    private val newsSource: NewsSource
) {
    suspend fun getNewsPosts(): List<NewsPost> {
        return newsSource.getNewsPosts().toUiNewsPosts()
    }

    suspend fun getDetailedNews(link: String): NewsDetailedPost {
        return newsSource.getDetailedNewsPost(link).toUiNewsDetailedPosts()
    }
}
