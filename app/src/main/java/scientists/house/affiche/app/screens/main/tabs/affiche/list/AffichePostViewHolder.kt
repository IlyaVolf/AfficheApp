package scientists.house.affiche.app.screens.main.tabs.affiche.list

import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.ItemAffichePostBinding
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.app.screens.base.BaseViewHolder
import scientists.house.affiche.app.utils.ResourcesUtils
import scientists.house.affiche.app.utils.image_loader.ImageLoader

class AffichePostViewHolder(
    private val viewBinding: ItemAffichePostBinding,
    private val onItemClick: (AffichePost) -> Unit
) : BaseViewHolder<AffichePost>(viewBinding) {

    override fun bindItem(item: AffichePost) {
        viewBinding.apply {
            root.setOnClickListener {
                onItemClick(item)
            }

            loadAffichePostPreview(item.imgUrl.orEmpty())
            iapMtvTitle.text = item.title
            iapMtvDate.text = item.performanceDate
        }
    }

    private fun loadAffichePostPreview(url: String) {
        ImageLoader
            .load(url)
            .error(R.drawable.img_not_found)
            .placeholder(R.drawable.img_not_found)
            .centerCrop()
            .roundedCorners(ResourcesUtils.getPxByDp(4f))
            .into(viewBinding.iapfIvPost)
    }
}