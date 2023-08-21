package scientists.house.affiche.app.model.news.entities

import scientists.house.affiche.sources.news.entities.NewsPostDataEntity as NewsPostSource

data class NewsPost(
    val title: String?,
    val text: String?,
    val date: String?,
    val detailsLink: String?,
    val prevPageLink: String?,
    val nextPageLink: String?,
    val page: String?
)
