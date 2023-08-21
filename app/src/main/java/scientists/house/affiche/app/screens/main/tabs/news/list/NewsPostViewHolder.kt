package scientists.house.affiche.app.screens.main.tabs.news.list

import scientists.house.affiche.app.databinding.ItemNewsPostBinding
import scientists.house.affiche.app.screens.base.BaseViewHolder
import scientists.house.affiche.app.model.news.entities.NewsPost
import scientists.house.affiche.app.utils.visible

class NewsPostViewHolder(
    private val viewBinding: ItemNewsPostBinding,
    private val onItemClick: (NewsPost) -> Unit
) : BaseViewHolder<NewsPost>(viewBinding) {

    override fun bindItem(item: NewsPost) {
        viewBinding.apply {
            resetView()

            root.setOnClickListener {
                onItemClick(item)
            }

            item.title?.let {
                inewsMtvTitle.text = it
            } ?: run {
                inewsMtvTitle.visible = false
            }

            item.text?.let {
                inewsMtvText.text = it
            } ?: run {
                inewsMtvText.visible = false
            }

            item.date?.let {
                inewsMtvDate.text = it
            } ?: run {
                inewsMtvDate.visible = false
            }
        }
    }

    private fun ItemNewsPostBinding.resetView() {
        inewsMtvTitle.visible = true
        inewsMtvText.visible = true
        inewsMtvDate.visible = true
    }
}
