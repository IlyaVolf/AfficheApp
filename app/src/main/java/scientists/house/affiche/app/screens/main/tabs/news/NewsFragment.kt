package scientists.house.affiche.app.screens.main.tabs.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentNewsBinding
import scientists.house.affiche.app.screens.base.BaseFragment

@AndroidEntryPoint
class NewsFragment : BaseFragment(R.layout.fragment_news) {

    override val viewModel by viewModels<NewsViewModel>()

    private lateinit var binding: FragmentNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)

        // binding.resultView.setTryAgainAction { viewModel.reload() }
    }
}
