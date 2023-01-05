package scientists.house.affiche.sources.affiche

import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import scientists.house.affiche.app.Consts.DUSORAN_EVENTS_URL
import scientists.house.affiche.app.Consts.DUSORAN_URL
import scientists.house.affiche.app.model.affiche.AfficheSource
import scientists.house.affiche.sources.affiche.entitites.AfficheDetailedPost
import scientists.house.affiche.sources.affiche.entitites.AffichePost
import javax.inject.Inject
import javax.inject.Singleton


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

        val age = element
            .select("div[class=srh-jumbotron-age-rating h1 mb-0]")
            .text()

        val data = element.select("div[class=srh-factoid-content]").text()
            .replace("Место проведения", "/")
            .replace("Время", "/")
            .replace("Купить билет", "/")
            .replace("Билет", "/")
            .split("/")

        val place = data[0].trim()
        val time = data[1].trim()
        val price = data[2].trim()

        val buyLink = element.select("div[class=srh-factoid-content]")
            .select("a")
            .attr("href")

        /*val aboutBlocks = element.select("div[style=text-align: justify;]").textNodes()
        var res = ""

        for (block in aboutBlocks) {
            res += block.text() + "\n"
        }

        res = res
            .replace("\n ".toRegex(), "\n")
            .replace("\n+".toRegex(), "\n\n")

        val aboutBlocks2 = element
            .select("div[class=srh-blog-item]")
            .select("p")
            .textNodes()

        var res2 = ""

        for (block in aboutBlocks2) {
            res2 += block.text() + "\n"
        }

        res2 = res2
            .replace("\n ".toRegex(), "\n")
            .replace("\n+".toRegex(), "\n\n")

        val about = res + res2*/

        /*var aaas = element.select("div[class=srh-blog-item]").textNodes().filter {
            it.text().replace("\n+ +".toRegex(), "-") != "-"
        }
        var bbb = element.select("div[class=srh-blog-item]").text()
        var bbbs = mutableListOf<String>()

        for (aaa in aaas) {
            val aaaas = bbb.split(aaa.text())
            for (aaaa in aaaas)
                bbbs.add(aaaa)
        }*/

        var aaas = element.select("div[class=srh-blog-item]").first()

        traverseTree(aaas)
        val formattedList = processText()
        val about = stickText(formattedList)
        cleanData()

        return AfficheDetailedPost(
            title = title,
            imgUrl = imgUrl,
            date = date,
            weekDay = weekDay,
            age = age,
            place = place,
            time = time,
            price = price,
            buyLink = buyLink,
            about = about
        )
    }

    var list: MutableList<String> = mutableListOf()

    private fun traverseTree(aaas: Node) {
        if (aaas.childNodeSize() == 0 && (aaas as? TextNode) != null) {
            list.add(aaas.text())
        }
        for (child in aaas.childNodes()) {
            traverseTree(child)
        }
    }

    private fun processText(): MutableList<String> {
        val res = mutableListOf<String>()

        var flag = true
        var counter = 0
        for (i in list.indices) {
            if (flag) {
                if (Regex("О событии").matches(list[i])) {
                    continue
                }
                if (Regex("Поделиться:").containsMatchIn(list[i])) {
                    continue
                }
                if (Regex(" +").matches(list[i])) {
                    if (counter == 0 && i != 0 && i != (list.size - 1)) {
                        res.add(list[i])
                    }
                    counter++
                    continue
                }
                if (Regex("\n+").matches(list[i])) {
                    if (counter == 0 && i != 0 && i != (list.size - 1)) {
                        res.add(list[i])
                    }
                    counter++
                    continue
                }
                if (Regex("Продолжительность").containsMatchIn(list[i])) {
                    res.add("\n" + list[i])
                    continue
                }
                if (Regex("–").matches(list[i]) && i > 0 && i < (list.size - 1)) {
                    res.removeLast()
                    res.add(list[i - 1] + list[i] + list[i + 1])
                    flag = false
                    continue
                }
                counter = 0
                res.add(list[i])
            } else {
                flag = true
            }
        }

        return res
    }

    private fun stickText(list: MutableList<String>): String {
        var res = ""

        for (i in list.indices) {
            res += if (i < list.size - 1) {
                list[i].trim() + "\n"
            } else {
                list[i].trim()
            }
        }

        return res
    }

    private fun cleanData() {
        list.clear()
    }
}
