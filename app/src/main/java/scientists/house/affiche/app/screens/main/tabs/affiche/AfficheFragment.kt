package scientists.house.affiche.app.screens.main.tabs.affiche

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentAfficheBinding
import scientists.house.affiche.app.utils.DataHolder
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.app.screens.base.BaseFragment
import scientists.house.affiche.app.screens.main.tabs.affiche.list.AfficheAdapter
import scientists.house.affiche.app.utils.viewBinding
import scientists.house.affiche.app.utils.visible

@AndroidEntryPoint
class AfficheFragment : BaseFragment(R.layout.fragment_affiche) {

    override val viewModel by viewModels<AfficheViewModel>()

    private val binding by viewBinding<FragmentAfficheBinding>()

    private val onItemClick: (AffichePost) -> Unit = { affichePost ->
        val direction = AfficheFragmentDirections.actionAffichePostToAffichePostDetails(
            postTitle = affichePost.title.orEmpty(),
            postLink = affichePost.detailsLink.orEmpty()
        )
        findNavController().navigate(direction)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AfficheAdapter(onItemClick)
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

                    binding.faRvAffichePosts.adapter = adapter
                    adapter.setupItems(holder.data)
                }
                is DataHolder.ERROR -> {
                    binding.faILoading.root.visible = false
                    binding.faIError.root.visible = true
                    binding.faClContent.visible = false
                }
            }
        }
    }

    override fun setupViews() {
        super.setupViews()
        binding.faIError.veMbTryAgain.setOnClickListener {
            viewModel.load()
        }
    }
}
