package scientists.house.affiche.app.utils

import androidx.lifecycle.MutableLiveData
import scientists.house.affiche.app.model.DataHolder

typealias ObservableHolder<T> = MutableLiveData<DataHolder<T>>
