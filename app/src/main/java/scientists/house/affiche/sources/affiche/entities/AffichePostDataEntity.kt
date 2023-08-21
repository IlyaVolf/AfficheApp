package scientists.house.affiche.sources.affiche.entities

import scientists.house.affiche.app.model.affiche.entities.AffichePost

data class AffichePostDataEntity(
    val title: String?,
    val imgUrl: String?,
    val number: String?,
    val month: String?,
    val detailsLink: String?
)
fun List<AffichePostDataEntity>.toAffichePosts(): List<AffichePost> = map {
    AffichePost(
        title = it.title,
        imgUrl = it.imgUrl,
        number = it.number,
        month = it.month,
        detailsLink = it.detailsLink
    )
}
