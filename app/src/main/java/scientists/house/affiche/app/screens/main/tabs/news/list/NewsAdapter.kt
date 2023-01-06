package scientists.house.affiche.app.screens.main.tabs.news.list

import android.view.LayoutInflater
import android.view.ViewGroup
import scientists.house.affiche.app.databinding.ItemNewsPostBinding
import scientists.house.affiche.app.model.news.entities.NewsPost
import scientists.house.affiche.app.screens.base.BaseAdapter
import scientists.house.affiche.app.screens.base.BaseViewHolder

class NewsAdapter(
    private val onItemClick: (NewsPost) -> Unit
) : BaseAdapter<BaseViewHolder<NewsPost>, NewsPost>() {

    override fun takeViewHolder(parent: ViewGroup): BaseViewHolder<NewsPost> =
        NewsPostViewHolder(
            viewBinding = ItemNewsPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<NewsPost> =
        takeViewHolder(parent)
}
