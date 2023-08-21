package scientists.house.affiche.app.model.affiche.entities

data class AffichePost(
    val title: String?,
    val imgUrl: String?,
    val number: String?,
    val month: String?,
    val detailsLink: String?
) {
    val performanceDate get() = "${number.orEmpty()} ${month.orEmpty()}".trim().ifEmpty { null }
}