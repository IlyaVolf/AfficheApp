package scientists.house.affiche.app.model.affiche

import scientists.house.affiche.app.model.affiche.entities.AffichePost

interface AfficheSource {

    suspend fun getAffichePosts(): List<AffichePost>
}
