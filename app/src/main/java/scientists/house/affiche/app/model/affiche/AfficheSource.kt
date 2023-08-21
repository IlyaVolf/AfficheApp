package scientists.house.affiche.app.model.affiche

import scientists.house.affiche.sources.affiche.entities.AfficheDetailedPostDataEntity
import scientists.house.affiche.sources.affiche.entities.AffichePostDataEntity

interface AfficheSource {

    suspend fun getAffichePosts(): List<AffichePostDataEntity>

    suspend fun getDetailedAffichePost(link: String): AfficheDetailedPostDataEntity
}
