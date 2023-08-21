package scientists.house.affiche.app.model.affiche

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.affiche.entities.AfficheDetailedPost
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.sources.affiche.entities.toAfficheDetailedPosts
import scientists.house.affiche.sources.affiche.entities.toAffichePosts

@Singleton
class AfficheRepository @Inject constructor(
    private val afficheSource: AfficheSource
) {
    suspend fun getAffichePosts(): List<AffichePost> {
                return afficheSource.getAffichePosts().toAffichePosts()
    }

    suspend fun getDetailedAffiche(link: String): AfficheDetailedPost {
        return afficheSource.getDetailedAffichePost(link).toAfficheDetailedPosts()
    }
}
