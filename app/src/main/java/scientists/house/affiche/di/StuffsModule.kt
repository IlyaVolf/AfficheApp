package scientists.house.affiche.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import scientists.house.affiche.app.utils.logger.LogCatLogger
import scientists.house.affiche.app.utils.logger.Logger

/**
 * Module for providing [Logger] implementation based on
 * system [Log] class.
 */
@Module
@InstallIn(SingletonComponent::class)
class StuffsModule {

    /**
     * We don't need scope annotation here because LogCatHolder is
     * 'object' (already singleton)
     */
    @Provides
    fun provideLogger(): Logger {
        return LogCatLogger
    }
}
