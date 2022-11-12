package scientists.house.affiche.app.model.planned

import scientists.house.affiche.app.model.affiche.entities.AffichePost

interface PlannedSource {
    suspend fun getPlannedAffichePosts(): List<AffichePost>

    suspend fun removeAffichePost(postId: Long)
}
