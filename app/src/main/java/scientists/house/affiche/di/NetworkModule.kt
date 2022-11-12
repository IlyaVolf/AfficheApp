package scientists.house.affiche.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import scientists.house.affiche.app.Consts

/**
 * This module provides instances for library network classes:
 * - [Moshi]
 * - [OkHttpClient]
 * - [Retrofit]
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
