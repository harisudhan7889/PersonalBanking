package au.com.nab.data

import androidx.lifecycle.MutableLiveData

/**
 * @author Hari Hara Sudhan. N
 */
interface DataSource<O> {
    fun getObserver(): MutableLiveData<O>
    fun onCleared()
}