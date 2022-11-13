package scientists.house.affiche.app.model.affiche

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.app.model.affiche.entities.toUiAffichePosts

@Singleton
class AfficheRepository @Inject constructor(
    private val afficheSource: AfficheSource
) {
    suspend fun getAffichePosts(): List<AffichePost> {
        return afficheSource.getAffichePosts().toUiAffichePosts()
    }
}
