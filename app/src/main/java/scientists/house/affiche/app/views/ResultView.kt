package scientists.house.affiche.app.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.PartResultViewBinding
import scientists.house.affiche.app.model.BackendException
import scientists.house.affiche.app.model.ConnectionException
import scientists.house.affiche.app.model.Empty
import scientists.house.affiche.app.model.Error
import scientists.house.affiche.app.model.Pending
import scientists.house.affiche.app.model.Result
import scientists.house.affiche.app.model.Success
import scientists.house.affiche.app.screens.base.BaseFragment

/**
 * Display progress-bar for [Pending] result, error message and try again button
 * for [Error] result and nothing else for [Empty] and [Success] results
 */
class ResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: PartResultViewBinding
    private var tryAgainAction: (() -> Unit)? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_result_view, this, true)
        binding = PartResultViewBinding.bind(this)
    }

    /**
     * Assign an action for 'Try Again' button.
     */
    fun setTryAgainAction(action: () -> Unit) {
        this.tryAgainAction = action
    }

    /**
     * Set the current result to be displayed to the user.
     */
    fun <T> setResult(fragment: BaseFragment, result: Result<T>) {
        binding.messageTextView.isVisible = result is Error<*>
        binding.errorButton.isVisible = result is Error<*>
        binding.progressBar.isVisible = result is Pending<*>
        if (result is Error) {
            Log.e(javaClass.simpleName, "Error", result.error)
            val message = when (result.error) {
                is ConnectionException -> context.getString(R.string.network_connection_error)
                is BackendException -> result.error.message
                else -> context.getString(R.string.network_internal_error)
            }
            binding.messageTextView.text = message
            renderTryAgainButton()
        }
    }

    private fun renderTryAgainButton() {
        binding.errorButton.setOnClickListener { tryAgainAction?.invoke() }
        binding.errorButton.setText(R.string.action_try_again)
    }
}
