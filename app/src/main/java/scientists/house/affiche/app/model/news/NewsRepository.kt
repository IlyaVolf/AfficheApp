package scientists.house.affiche.app.model.news

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.news.entities.NewsDetailedPost
import scientists.house.affiche.app.model.news.entities.NewsPost
import scientists.house.affiche.sources.news.entities.toNewsDetailedPosts
import scientists.house.affiche.sources.news.entities.toNewsPosts

@Singleton
class NewsRepository @Inject constructor(
    private val newsSource: NewsSource
) {
    suspend fun getNewsPosts(): List<NewsPost> {
        return newsSource.getNewsPosts().toNewsPosts()
    }

    suspend fun getDetailedNews(link: String): NewsDetailedPost {
        return newsSource.getDetailedNewsPost(link).toNewsDetailedPosts()
    }
}
