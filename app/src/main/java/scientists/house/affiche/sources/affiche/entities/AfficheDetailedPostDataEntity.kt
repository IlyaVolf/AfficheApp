package scientists.house.affiche.sources.affiche.entities

import scientists.house.affiche.app.model.affiche.entities.AfficheDetailedPost

data class AfficheDetailedPostDataEntity(
    val title: String?,
    val imgUrl: String?,
    val date: String?,
    val weekDay: String?,
    val age: String?,
    val place: String?,
    val time: String?,
    val price: String?,
    val buyLink: String?,
    val about: String?,
)

fun AfficheDetailedPostDataEntity.toAfficheDetailedPosts(): AfficheDetailedPost =
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