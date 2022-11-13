package scientists.house.affiche.sources.affiche

import javax.inject.Inject
import javax.inject.Singleton
import org.jsoup.Jsoup
import scientists.house.affiche.app.Consts.DUSORAN_EVENTS_URL
import scientists.house.affiche.app.Consts.DUSORAN_URL
import scientists.house.affiche.app.model.affiche.AfficheSource
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

            val performanceNumber = element
                .select("div[class=srh-card-event-date]")
                .select("span")
                .text()

            val performanceMonth = element
                .select("a.text-lowercase")
                .text()

            val affichePost = AffichePost(
                title = title,
                imgUrl = imgUrl,
                performanceNumber = performanceNumber,
                performanceMonth = performanceMonth
            )

            affichePosts.add(affichePost)
        }

        return affichePosts
    }
}
