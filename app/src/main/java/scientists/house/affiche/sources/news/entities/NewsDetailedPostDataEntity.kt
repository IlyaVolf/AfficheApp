package scientists.house.affiche.sources.news.entities

import scientists.house.affiche.app.model.news.entities.NewsDetailedPost

data class NewsDetailedPostDataEntity(
    val title: String?,
    val about: String?,
    val date: String?,
    val photos: List<String>
)

fun NewsDetailedPostDataEntity.toNewsDetailedPosts(): NewsDetailedPost =
    NewsDetailedPost(
        title = title,
        about = about,
        date = date,
        photos = photos
    )