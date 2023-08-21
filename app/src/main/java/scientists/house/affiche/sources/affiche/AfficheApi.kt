package scientists.house.affiche.sources.affiche

import scientists.house.affiche.sources.affiche.entities.AffichePostDataEntity

interface AfficheApi {

    suspend fun getAffiche(): List<AffichePostDataEntity>

    suspend fun setPlannedAffichePost(postId: Long, isSelected: Boolean)
}
