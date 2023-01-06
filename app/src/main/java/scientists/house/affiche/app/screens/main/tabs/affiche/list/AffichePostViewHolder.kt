package scientists.house.affiche.app.screens.main.tabs.affiche.list

import scientists.house.affiche.app.extensions.loadImage

import scientists.house.affiche.app.databinding.ItemAffichePostBinding
import scientists.house.affiche.app.screens.base.BaseViewHolder
import scientists.house.affiche.app.model.affiche.entities.AffichePost
import scientists.house.affiche.app.utils.visible

class AffichePostViewHolder(
    private val viewBinding: ItemAffichePostBinding,
    private val onItemClick: (AffichePost) -> Unit
) : BaseViewHolder<AffichePost>(viewBinding) {

    override fun bindItem(item: AffichePost) {
        viewBinding.apply {
            resetView()

            root.setOnClickListener {
                onItemClick(item)
            }

            iapfIvPost.loadImage(item.imgUrl.orEmpty())

            item.title?.let {
                iapMtvTitle.text = it
            } ?: run {
                iapMtvTitle.visible = false
            }

            item.performanceDate?.let {
                iapMtvDate.text = it
            } ?: run {
                iapMtvDate.visible = false
            }
        }
    }

    private fun ItemAffichePostBinding.resetView() {
        iapMtvTitle.visible = true
        iapMtvDate.visible = true
    }
}
