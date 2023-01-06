package scientists.house.affiche.app.model.news.entities

import scientists.house.affiche.sources.news.entities.NewsPost as NewsPostSource

data class NewsPost(
    val title: String?,
    val text: String?,
    val date: String?,
    val detailsLink: String?,
    val prevPageLink: String?,
    val nextPageLink: String?,
    val page: String?
) {
    //val performanceDate get() = "${number.orEmpty()} ${month.orEmpty()}".trim().ifEmpty { null }
}

fun List<NewsPostSource>.toUiNewsPosts(): List<NewsPost> = map {
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
