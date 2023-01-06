package scientists.house.affiche.app.model.news.entities

import scientists.house.affiche.sources.news.entities.NewsDetailedPost as NewsDetailedPostSource

data class NewsDetailedPost(
    val title: String?,
    val about: String?,
    val date: String?,
) {
    //val performanceDate get() = "${number.orEmpty()} ${month.orEmpty()}".trim().ifEmpty { null }
}

fun NewsDetailedPostSource.toUiNewsDetailedPosts(): NewsDetailedPost =
    NewsDetailedPost(
        title = title,
        about = about,
        date = date
    )