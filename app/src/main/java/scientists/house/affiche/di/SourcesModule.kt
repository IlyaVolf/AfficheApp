package scientists.house.affiche.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import scientists.house.affiche.app.model.affiche.AfficheSource
import scientists.house.affiche.app.model.news.NewsSource
import scientists.house.affiche.app.model.planned.PlannedSource
import scientists.house.affiche.sources.affiche.JsoupAfficheSource
import scientists.house.affiche.sources.news.JsoupNewsSource
import scientists.house.affiche.sources.planned.RoomPlannedSource

/**
 * This module binds concrete sources implementations to their
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindAfficheSource(
        jsoupAfficheSource: JsoupAfficheSource
    ): AfficheSource

    @Binds
    abstract fun bindPlannedSource(
        roomPlannedSource: RoomPlannedSource
    ): PlannedSource

    @Binds
    abstract fun bindNewsSource(
        jsoupNewsSource: JsoupNewsSource
    ): NewsSource
}
