package scientists.house.affiche.app.model.affiche.entities

import scientists.house.affiche.sources.affiche.entitites.AffichePost as AffichePostSource

data class AffichePost(
    val id: Long? = null,
    val title: String?,
    val imgUrl: String?,
    val performanceNumber: String?,
    val performanceMonth: String?
) {
    val performanceDate get() = "$performanceNumber $performanceMonth"
}

fun List<AffichePostSource>.toUiAffichePosts(): List<AffichePost> = map {
    AffichePost(
        title = it.title,
        imgUrl = it.imgUrl,
        performanceNumber = it.performanceNumber,
        performanceMonth = it.performanceMonth
    )
}
