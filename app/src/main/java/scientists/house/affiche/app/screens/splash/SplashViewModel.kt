package scientists.house.affiche.app.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import scientists.house.affiche.app.utils.MutableLiveEvent
import scientists.house.affiche.app.utils.publishEvent
import scientists.house.affiche.app.utils.share

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(true)
        }
    }
}
