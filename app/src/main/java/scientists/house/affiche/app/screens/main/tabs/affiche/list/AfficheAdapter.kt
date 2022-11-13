package scientists.house.affiche.app.screens.main.tabs.affiche.list

import android.view.LayoutInflater
import android.view.ViewGroup
import scientists.house.affiche.app.databinding.ItemAffichePostBinding
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.app.screens.base.BaseAdapter
import scientists.house.affiche.app.screens.base.BaseViewHolder

class AfficheAdapter(
    private val onItemClick: (AffichePost) -> Unit
) : BaseAdapter<BaseViewHolder<AffichePost>, AffichePost>() {

    override fun takeViewHolder(parent: ViewGroup): BaseViewHolder<AffichePost> =
        AffichePostViewHolder(
            viewBinding = ItemAffichePostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AffichePost> =
        takeViewHolder(parent)
}
