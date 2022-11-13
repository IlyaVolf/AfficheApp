package scientists.house.affiche.app.utils

import android.view.View

/**
 * Extension для удобного использования флага видимости вью
 */
var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
