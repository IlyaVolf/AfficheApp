package scientists.house.affiche.sources.news.entities

import scientists.house.affiche.app.model.news.entities.NewsPost

data class NewsPostDataEntity(
    val title: String?,
    val text: String?,
    val date: String?,
    val detailsLink: String?,
    val prevPageLink: String?,
    val nextPageLink: String?,
    val page: String?,
)

fun List<NewsPostDataEntity>.toNewsPosts(): List<NewsPost> = map {
    NewsPost(
        title = it.title,
        text = it.text,
        date = it.date,
        detailsLink = it.detailsLink,
        prevPageLink = it.prevPageLink,
        nextPageLink = it.nextPageLink,
        page = it.page
    )
}
