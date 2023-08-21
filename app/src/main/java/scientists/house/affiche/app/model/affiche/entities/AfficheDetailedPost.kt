package scientists.house.affiche.app.model.affiche.entities

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