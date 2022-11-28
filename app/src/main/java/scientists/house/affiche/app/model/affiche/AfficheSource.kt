package scientists.house.affiche.app.model.affiche

import scientists.house.affiche.sources.affiche.entitites.AfficheDetailedPost
import scientists.house.affiche.sources.affiche.entitites.AffichePost

interface AfficheSource {

    suspend fun getAffichePosts(): List<AffichePost>

    suspend fun getDetailedAffichePost(link: String): AfficheDetailedPost
}
