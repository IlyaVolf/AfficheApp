package scientists.house.affiche.app.screens.main.tabs.affiche

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentAffichePostDetailsBinding
import scientists.house.affiche.app.extensions.loadImage
import scientists.house.affiche.app.model.DataHolder
import scientists.house.affiche.app.model.affiche.entities.AfficheDetailedPost
import scientists.house.affiche.app.screens.base.BaseFragment
import scientists.house.affiche.app.utils.viewModelCreator
import scientists.house.affiche.app.utils.visible

@AndroidEntryPoint
class AffichePostDetailsFragment : BaseFragment(R.layout.fragment_affiche_post_details) {

    @Inject
    lateinit var factory: AffichePostDetailsViewModel.Factory
    override val viewModel by viewModelCreator {
        factory.create(args.postLink)
    }

    private lateinit var binding: FragmentAffichePostDetailsBinding

    private val args by navArgs<AffichePostDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAffichePostDetailsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
        super.setupViews()
        binding.apply {
            fapdIError.veMbTryAgain.setOnClickListener {
                viewModel.load(args.postLink)
            }
        }
    }

    override fun observeViewModel() {
        viewModel.afficheDetailedPost.observe(viewLifecycleOwner) { holder ->
            when (holder) {
                DataHolder.LOADING -> {
                    binding.apply {
                        fapdILoading.root.visible = true
                        fapdIError.root.visible = false
                        fapdLlContent.visible = false
                    }
                }
                is DataHolder.READY -> {
                    binding.apply {
                        fapdILoading.root.visible = false
                        fapdIError.root.visible = false
                        fapdLlContent.visible = true
                    }

                    setupDetailedAffiche(holder.data)
                }
                is DataHolder.ERROR -> {
                    binding.apply {
                        fapdILoading.root.visible = false
                        fapdIError.root.visible = true
                        fapdLlContent.visible = false
                    }
                }
            }
        }
    }

    private fun setupDetailedAffiche(data: AfficheDetailedPost) {
        binding.apply {
            data.title?.let {
                fapdMtvTitle.text = it
            } ?: run {
                fapdMtvTitle.visible = false
            }

            data.performanceDate?.let {
                fapdMtvDate.text = it
            } ?: run {
                fapdMtvDate.visible = false
            }

            if (data.age != "") {
                fapdMtvAge.text = data.age
            } else {
                fapdMtvAge.visible = false
            }

            data.imgUrl?.let {
                fapdIvPost.loadImage(it)
            } ?: run {
                fapdIvPost.visible = false
            }

            aboutPlace.text = getString(R.string.affiche_place)
            if (data.place != "") {
                fapdMtvPlace.text = data.place
            } else {
                fapdMtvPlace.text = getString(R.string.no_data)
            }

            aboutTime.text = getString(R.string.affiche_time)
            if (data.time != "") {
                fapdMtvTime.text = data.time
            } else {
                fapdMtvTime.text = getString(R.string.no_data)
            }

            aboutPrice.text = getString(R.string.affiche_price)
            if (data.price != "") {
                fapdMtvPrice.text = data.price
            } else {
                fapdMtvPrice.text = getString(R.string.no_data)
            }

            data.buyLink?.let { link ->
                fapdMbBuy.setOnClickListener {
                    //viewModel.onBuyButtonClicked(link)
                    val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(link))
                    startActivity(intent)
                }
            } ?: run {
                fapdMbBuy.visible = false
            }

            data.about?.let {
                fapdMtvAbout.text = it
                aboutText.text = getString(R.string.affiche_about)
            } ?: run {
                fapdMtvAbout.visible = false
                aboutText.visible = false
            }
        }
    }
}
