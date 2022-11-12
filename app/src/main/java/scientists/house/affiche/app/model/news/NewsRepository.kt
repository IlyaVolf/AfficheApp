package scientists.house.affiche.app.model.news

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsSource: NewsSource
) {
    // todo добавить cash
}
