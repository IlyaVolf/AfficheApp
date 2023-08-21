package scientists.house.affiche.app.extensions

import android.widget.ImageView
import scientists.house.affiche.app.R
import scientists.house.affiche.app.utils.ResourcesUtils
import scientists.house.affiche.app.utils.image_loader.ImageLoader

fun ImageView.loadImage(url: String) {
    ImageLoader
        .load(url)
        .error(R.drawable.img_not_found)
        .placeholder(R.drawable.img_not_found)
        .centerCrop()
        .roundedCorners(ResourcesUtils.getPxByDp(4f))
        .into(this)
}
