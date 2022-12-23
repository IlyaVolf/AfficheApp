package scientists.house.affiche.sources.affiche

import javax.inject.Inject
import javax.inject.Singleton
import org.jsoup.Jsoup
import scientists.house.affiche.app.Consts.DUSORAN_EVENTS_URL
import scientists.house.affiche.app.Consts.DUSORAN_URL
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

            val detailsLink = element
                .select("div[class=srh-card-event-footer]")
                .select("a.text-dark")
                .attr("href")
                .let { url -> "$DUSORAN_URL$url" }

            val affichePost = AffichePost(
                title = title,
                imgUrl = imgUrl,
                number = number,
                month = month,
                detailsLink = detailsLink
            )

            affichePosts.add(affichePost)
        }

        return affichePosts
    }

    // todo
    override suspend fun getDetailedAffichePost(link: String): AfficheDetailedPost {
        val document = Jsoup.connect(link).get()
        val element = document.select("section[class=srh-event -detail]")

        val title = element
            .select("h1[class=title]")
            .text()

        val imgUrl = element
            .select("div[class=srh-jumbotron-media]")
            .select("img")
            .attr("src")
            .let { url -> "$DUSORAN_URL$url" }

        val date = element
            .select("h2[class=subtitle]")
            .select("span[class=text-lowercase srh-color-primary-0]")
            .text()

        val weekDay = element
            .select("h2[class=subtitle]")
            .select("span[class=text-lowercase]")
            .text()

        /*val place = element
            .select("dev[class=srh-factoid-content]")
            .select("figure")
            .select("div")
            .text()*/

        val age = element
            .select("div[class=srh-jumbotron-age-rating h1 mb-0]")
            .text()

        val about = element
            .select("div[class=srh-blog-item]")
            .select("p")
            .text()

        return AfficheDetailedPost(
            title = title,
            imgUrl = imgUrl,
            date = date,
            weekDay = weekDay,
            age = age,
            place = null,
            time = null,
            price = null,
            buyLink = null,
            about = about
        )
    }
}
