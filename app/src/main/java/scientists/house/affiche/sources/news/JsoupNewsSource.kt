package scientists.house.affiche.sources.news

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scientists.house.affiche.app.Consts.DUSORAN_URL
import scientists.house.affiche.app.State
import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.news.NewsSource
import scientists.house.affiche.sources.news.entities.NewsDetailedPostDataEntity
import scientists.house.affiche.sources.news.entities.NewsPostDataEntity

@Singleton
class JsoupNewsSource @Inject constructor() : NewsSource {

    override suspend fun getNewsPosts(): List<NewsPostDataEntity> {
        val newsPostDataEntities = mutableListOf<NewsPostDataEntity>()

        val document = Jsoup.connect(State.DUSORAN_NEWS_URL).get()
        val elements = document.select("article[class=srh-blog-item]")
        elements.forEach { element ->
            val title = ((element
                .childNodes()[9] as Element)
                .childNodes()[1] as Element)
                .select("div")
                .attr("data-title")

            val text = (element
                .childNodes()[5] as Element)
                .text()

            val date = (element
                .childNodes()[1] as Element)
                .text()

            val detailsLink = ((element
                .childNodes()[9] as Element)
                .childNodes()[1] as Element)
                .select("div")
                .attr("data-url")

            val pageElements = document.select("div[class=srh-blog-footer]")

            val page = pageElements.select("li[class=page-item active]").text()

            val pagesLink =
                pageElements.select("li[class=page-item disabled] , li[class=page-item]")

            var prevPageLink = pagesLink.first().select("a").attr("href")
            prevPageLink = if (prevPageLink == "#") {
                ""
            } else {
                "$DUSORAN_URL$prevPageLink"
            }

            var nextPageLink = pagesLink.last().select("a").attr("href")
            nextPageLink = if (nextPageLink == "#") {
                ""
            } else {
                "$DUSORAN_URL$nextPageLink"
            }

            val newsPostDataEntity = NewsPostDataEntity(
                title = title,
                text = text,
                date = date,
                detailsLink = detailsLink,
                prevPageLink = prevPageLink,
                nextPageLink = nextPageLink,
                page = page
            )

            newsPostDataEntities.add(newsPostDataEntity)
        }

        return newsPostDataEntities
    }

    override suspend fun getDetailedNewsPost(link: String): NewsDetailedPostDataEntity {
        val document = Jsoup.connect(link).get()
        val elements = document.select("article[class=srh-blog-item]")

        val title = (elements[0]
            .childNodes()[1] as Element)
            .text()

        val date = (elements[0]
            .childNodes()[3] as Element)
            .text()

        val lines = elements[0].children().textNodes()

        val res = mutableListOf<String>()
        for (line in lines) {
            if (Regex("( |\n)+").matches(line.text())) {
                continue
            }
            res.add(line.text())
        }
        if (res[0] == title) {
            res.removeAt(0)
        }

        val about = stickText(res)

        val allImgs = document.select("img").mapNotNull { it.attr("src") }
        val photos = allImgs.filter {
            it.startsWith("/upload")
        }.map { "$DUSORAN_URL$it" }

        return NewsDetailedPostDataEntity(
            title = title,
            about = about,
            date = date,
            photos = photos
        )
    }

    private fun stickText(list: MutableList<String>): String {
        var res = ""

        for (i in list.indices) {
            res += if (i < list.size - 1) {
                list[i].trim() + "\n\n"
            } else {
                list[i].trim()
            }
        }

        return res
    }

}
