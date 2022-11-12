package scientists.house.affiche.app.model.planned

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlannedRepository @Inject constructor(
    private val plannedSource: PlannedSource
)
