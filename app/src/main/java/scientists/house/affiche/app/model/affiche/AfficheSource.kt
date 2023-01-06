package scientists.house.affiche.app.model.affiche

import scientists.house.affiche.sources.affiche.entities.AfficheDetailedPost
import scientists.house.affiche.sources.affiche.entities.AffichePost

interface AfficheSource {

    suspend fun getAffichePosts(): List<AffichePost>

    suspend fun getDetailedAffichePost(link: String): AfficheDetailedPost
}
