package scientists.house.affiche.sources.base

import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Retrofit

/**
 * All stuffs required for making HTTP-requests with Retrofit client and
 * for parsing JSON-messages.
 */
@Singleton
class RetrofitConfig @Inject constructor(
    val retrofit: Retrofit,
    val moshi: Moshi
)
