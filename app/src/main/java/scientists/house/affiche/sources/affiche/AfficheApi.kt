package scientists.house.affiche.sources.affiche

import scientists.house.affiche.sources.affiche.entitites.AffichePost

interface AfficheApi {

    suspend fun getAffiche(): List<AffichePost>

    suspend fun setPlannedAffichePost(postId: Long, isSelected: Boolean)
}
