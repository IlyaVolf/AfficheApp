package scientists.house.affiche.app.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import scientists.house.affiche.app.R

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity()
        .supportFragmentManager
        .findFragmentById(R.id.fragmentContainer) as NavHostFragment?

    return topLevelHost?.navController ?: findNavController()
}
