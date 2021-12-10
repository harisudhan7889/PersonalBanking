package au.com.nab.data

import androidx.lifecycle.MutableLiveData

/**
 * Base data source used to expose callback for output
 * and resource clear method.
 *
 * @author Hari Hara Sudhan. N
 */
interface DataSource<O> {
    fun getObserver(): MutableLiveData<O>
    fun onCleared()
}