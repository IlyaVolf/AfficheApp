package scientists.house.affiche.app.screens.main.tabs.affiche

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentAfficheBinding
import scientists.house.affiche.app.model.DataHolder
import scientists.house.affiche.app.screens.base.BaseFragment
import scientists.house.affiche.app.utils.visible

@AndroidEntryPoint
class AfficheFragment : BaseFragment(R.layout.fragment_affiche) {

    override val viewModel by viewModels<AfficheViewModel>()

    private lateinit var binding: FragmentAfficheBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAfficheBinding.bind(view)
    }

    override fun observeViewModel() {
        viewModel.affichePosts.observe(viewLifecycleOwner) { holder ->
            when (holder) {
                DataHolder.LOADING -> {
                    binding.faILoading.root.visible = true
                    binding.faIError.root.visible = false
                    binding.faClContent.visible = false
                }
                is DataHolder.READY -> {
                    binding.faILoading.root.visible = false
                    binding.faIError.root.visible = false
                    binding.faClContent.visible = true
                }
                is DataHolder.ERROR -> {
                    binding.faILoading.root.visible = false
                    binding.faIError.root.visible = true
                    binding.faClContent.visible = false
                }
            }
        }
    }
}
