package scientists.house.affiche.app.screens.main.tabs.news.list

import android.view.LayoutInflater
import android.view.ViewGroup
import scientists.house.affiche.app.databinding.ItemNewsPhotoBinding
import scientists.house.affiche.app.screens.base.BaseAdapter
import scientists.house.affiche.app.screens.base.BaseViewHolder

class PhotosAdapter(
    private val onItemClick: (Photo) -> Unit
) : BaseAdapter<BaseViewHolder<Photo>, Photo>() {

    override fun takeViewHolder(parent: ViewGroup): BaseViewHolder<Photo> =
        PhotosViewHolder(
            viewBinding = ItemNewsPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Photo> =
        takeViewHolder(parent)
}
