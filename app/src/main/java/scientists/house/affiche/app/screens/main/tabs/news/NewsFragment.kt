package scientists.house.affiche.app.screens.main.tabs.news

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.State.DUSORAN_NEWS_URL
import scientists.house.affiche.app.databinding.FragmentPostNewsBinding
import scientists.house.affiche.app.utils.DataHolder
import scientists.house.affiche.app.model.news.entities.NewsPost
import scientists.house.affiche.app.screens.base.BaseFragment
import scientists.house.affiche.app.screens.main.tabs.news.list.NewsAdapter
import scientists.house.affiche.app.utils.viewBinding
import scientists.house.affiche.app.utils.visible


@AndroidEntryPoint
class NewsFragment : BaseFragment(R.layout.fragment_post_news) {

    override val viewModel by viewModels<NewsViewModel>()

    private val binding by viewBinding<FragmentPostNewsBinding>()

    private val onItemClick: (NewsPost) -> Unit = { newsPost ->
        val direction = NewsFragmentDirections.actionNewsPostToNewsPostDetails(
            postTitle = newsPost.title.orEmpty(),
            postLink = newsPost.detailsLink.orEmpty()
        )
        findNavController().navigate(direction)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter(onItemClick)
    }

    override fun observeViewModel() {
        viewModel.newsPosts.observe(viewLifecycleOwner) { holder ->
            when (holder) {
                DataHolder.LOADING -> {
                    binding.fnILoading.root.visible = true
                    binding.fnIError.root.visible = false
                    binding.fnClContent.visible = false
                }
                is DataHolder.READY -> {
                    binding.fnILoading.root.visible = false
                    binding.fnIError.root.visible = false
                    binding.fnClContent.visible = true

                    binding.faRvNewsPosts.adapter = adapter
                    adapter.setupItems(holder.data)

                    setupButtons(holder.data[0])
                }
                is DataHolder.ERROR -> {
                    binding.fnILoading.root.visible = false
                    binding.fnIError.root.visible = true
                    binding.fnClContent.visible = false
                }
            }
        }
    }

    private fun goPage(page: String) {
        //Toast.makeText(requireContext(), "Страница $page", Toast.LENGTH_SHORT).show()
        setupViews()
        viewModel.load()
        observeViewModel()
    }

    override fun setupViews() {
        super.setupViews()
        binding.fnIError.veMbTryAgain.setOnClickListener {
            viewModel.load()
        }
    }

    private fun setupButtons(data: NewsPost) {
        if (data.prevPageLink != "") {
            binding.fnewsMbPrev.visible = true
            binding.fnewsMbPrev.setOnClickListener {
                DUSORAN_NEWS_URL = data.prevPageLink!!
                goPage((data.page!!.toInt() - 1).toString())
            }
        } else {
            binding.fnewsMbPrev.visible = false
        }

        if (data.nextPageLink != "") {
            binding.fnewsMbNext.visible = true
            binding.fnewsMbNext.setOnClickListener {
                DUSORAN_NEWS_URL = data.nextPageLink!!
                goPage((data.page!!.toInt() + 1).toString())
            }
        } else {
            binding.fnewsMbNext.visible = false
        }
    }
}
