package scientists.house.affiche.app.model.affiche.entities

import scientists.house.affiche.sources.affiche.entities.AffichePost as AffichePostSource

data class AffichePost(
    val title: String?,
    val imgUrl: String?,
    val number: String?,
    val month: String?,
    val detailsLink: String?
) {
    val performanceDate get() = "${number.orEmpty()} ${month.orEmpty()}".trim().ifEmpty { null }
}

fun List<AffichePostSource>.toUiAffichePosts(): List<AffichePost> = map {
    AffichePost(
        title = it.title,
        imgUrl = it.imgUrl,
        number = it.number,
        month = it.month,
        detailsLink = it.detailsLink
    )
}
