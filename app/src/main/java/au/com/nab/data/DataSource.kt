package au.com.nab.data

import androidx.lifecycle.MutableLiveData
import au.com.nab.domain.common.ViewState

/**
 * Base data source used to expose callback for output
 * and resource clear method.
 *
 * @author Hari Hara Sudhan. N
 */
interface DataSource<O> {
    fun getObserver(): MutableLiveData<ViewState<O>>
    fun onCleared()
    fun cache(item: O)
}