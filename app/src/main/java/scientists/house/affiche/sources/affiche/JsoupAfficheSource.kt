package scientists.house.affiche.sources.affiche

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.affiche.AfficheSource
import scientists.house.affiche.app.model.affiche.entities.AffichePost

@Singleton
class JsoupAfficheSource @Inject constructor() : AfficheSource {

    override suspend fun getAffichePosts(): List<AffichePost> {
        TODO("Not yet implemented")
    }
}
