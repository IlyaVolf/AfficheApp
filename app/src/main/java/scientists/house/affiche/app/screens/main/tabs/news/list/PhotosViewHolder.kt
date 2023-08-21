package scientists.house.affiche.app.screens.main.tabs.news.list

import scientists.house.affiche.app.databinding.ItemNewsPhotoBinding
import scientists.house.affiche.app.extensions.loadImage
import scientists.house.affiche.app.screens.base.BaseViewHolder

class PhotosViewHolder(
    private val viewBinding: ItemNewsPhotoBinding,
    private val onItemClick: (Photo) -> Unit
) : BaseViewHolder<Photo>(viewBinding) {

    override fun bindItem(item: Photo) {
        viewBinding.apply {
            newsPhotoIv.loadImage(item.uri)
        }
    }
}
