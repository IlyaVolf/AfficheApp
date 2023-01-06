package scientists.house.affiche.sources.news.entities

data class NewsPost(
    val title: String?,
    val text: String?,
    val date: String?,
    val detailsLink: String?,
    val prevPageLink: String?,
    val nextPageLink: String?,
    val page: String?
)
