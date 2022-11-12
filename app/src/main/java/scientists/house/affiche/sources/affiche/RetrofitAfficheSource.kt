package scientists.house.affiche.sources.affiche

import javax.inject.Inject
import javax.inject.Singleton
import scientists.house.affiche.app.model.affiche.AfficheSource
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.sources.base.BaseRetrofitSource
import scientists.house.affiche.sources.base.RetrofitConfig

@Singleton
class RetrofitAfficheSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), AfficheSource {

    private val afficheApi = retrofit.create(AfficheApi::class.java)

    override suspend fun getAffichePosts(): List<AffichePost> = wrapRetrofitExceptions {
        TODO("Not yet implemented")
    }

    override suspend fun setPlannedAffichePost(postId: Long, isSelected: Boolean) {
        TODO("Not yet implemented")
    }
}
