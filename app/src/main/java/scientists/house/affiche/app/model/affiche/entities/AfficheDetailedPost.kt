package scientists.house.affiche.app.model.affiche.entities

import scientists.house.affiche.sources.affiche.entitites.AfficheDetailedPost as AfficheDetailedPostSource

data class AfficheDetailedPost(
    val title: String?,
    val imgUrl: String?,
    val number: String?,
    val month: String?,
    val age: String?,
    val place: String?,
    val time: String?,
    val price: String?,
    val buyLink: String?,
    val about: String?,
) {
    val performanceDate get() = "${number.orEmpty()} ${month.orEmpty()}".trim().ifEmpty { null }
}

fun AfficheDetailedPostSource.toUiAfficheDetailedPosts(): AfficheDetailedPost =
    AfficheDetailedPost(
        title = title,
        imgUrl = imgUrl,
        number = date,
        month = weekDay,
        age = age,
        place = place,
        time = time,
        price = price,
        buyLink = buyLink,
        about = about
    )