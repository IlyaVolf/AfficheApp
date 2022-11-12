package scientists.house.affiche.sources.planned

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.app.model.planned.PlannedSource

@Singleton
class RoomPlannedSource @Inject constructor() : PlannedSource {

    override suspend fun getPlannedAffichePosts(): List<AffichePost> {
        TODO("Not yet implemented")
    }

    override suspend fun removeAffichePost(postId: Long) {
        TODO("Not yet implemented")
    }
}