package scientists.house.affiche.sources.affiche

import org.jsoup.Jsoup
import scientists.house.affiche.app.Consts.DUSORAN_EVENTS_URL
import scientists.house.affiche.app.Consts.DUSORAN_URL
import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.affiche.AfficheSource
import scientists.house.affiche.sources.affiche.entitites.AfficheDetailedPost
import scientists.house.affiche.sources.affiche.entitites.AffichePost

@Singleton
class JsoupAfficheSource @Inject constructor() : AfficheSource {

    override suspend fun getAffichePosts(): List<AffichePost> {
        val affichePosts = mutableListOf<AffichePost>()

        val document = Jsoup.connect(DUSORAN_EVENTS_URL).get()
        val elements = document.select("div[class=srh-card-event]")
        elements.forEach { element ->
            val title = element
                .select("a.text-dark")
                .text()

            val imgUrl = element
                .select("div[class=srh-card-event-body]")
                .select("img")
                .attr("src")
                .let { url -> "$DUSORAN_URL$url" }

            val number = element
                .select("div[class=srh-card-event-date]")
                .select("span")
                .text()

            val month = element
                .select("a.text-lowercase")
                .text()

            val affichePost = AffichePost(
                title = title,
                imgUrl = imgUrl,
                number = number,
                month = month,
                // todo
                detailsLink = null
            )

            affichePosts.add(affichePost)
        }

        return affichePosts
    }

    // todo
    override suspend fun getDetailedAffichePost(link: String): AfficheDetailedPost {
        return AfficheDetailedPost(
            title = "StundUp",
            imgUrl = "",
            number = "26",
            month = "ноября, суббота",
            age = "18+",
            place = "большой зал",
            time = "19:00",
            price = "от 1000 руб.",
            buyLink = "",
            about = "Информация о событии"
        )
    }
}
