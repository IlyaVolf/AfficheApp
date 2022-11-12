package scientists.house.affiche.app.model.affiche

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AfficheRepository @Inject constructor(
    private val afficheSource: AfficheSource
) {
    // todo добавить cash
}
