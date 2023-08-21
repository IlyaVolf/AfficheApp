package scientists.house.affiche.sources.planned

import scientists.house.affiche.sources.affiche.entities.AffichePostDataEntity

interface PlannedApi {
    suspend fun getPlannedAffichePosts(): List<AffichePostDataEntity>

    suspend fun removeAffichePost(postId: Long)
}
