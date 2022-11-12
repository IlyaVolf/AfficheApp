package scientists.house.affiche.app.screens.main.tabs.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentNewsDetailsBinding
import scientists.house.affiche.app.screens.base.BaseFragment

@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment(R.layout.fragment_news_details) {

    override val viewModel by viewModels<NewsViewModel>()

    private lateinit var binding: FragmentNewsDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsDetailsBinding.bind(view)
    }
}
