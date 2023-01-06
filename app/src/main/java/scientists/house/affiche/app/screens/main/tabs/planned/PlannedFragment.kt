package scientists.house.affiche.app.screens.main.tabs.planned

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentPlannedBinding
import scientists.house.affiche.app.newTrackLocation.LocationService
import scientists.house.affiche.app.screens.base.BaseFragment
import scientists.house.affiche.app.trackLocation.log
import scientists.house.affiche.app.utils.visible

@AndroidEntryPoint
class PlannedFragment : BaseFragment(R.layout.fragment_planned) {

    override val viewModel by viewModels<PlannedViewModel>()

    private lateinit var binding: FragmentPlannedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlannedBinding.bind(view)

        val context = activity!!.applicationContext

        binding.locationDescription.text = getString(R.string.tracking_description)

        binding.locationTurnOn.setOnClickListener {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                activity!!.startService(this)
                log("GOGOGO")
            }
        }

        binding.locationTurnOff.setOnClickListener {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
                activity!!.startService(this)
                log("STOPSTOPSTOP")
            }
        }

        // binding.resultView.setTryAgainAction { viewModel.reload() }
    }
}
