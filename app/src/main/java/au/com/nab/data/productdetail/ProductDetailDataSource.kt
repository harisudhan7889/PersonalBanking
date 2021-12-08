package au.com.nab.data.productdetail

import androidx.lifecycle.MutableLiveData
import au.com.nab.domain.ViewState
import au.com.nab.domain.productdetail.ProductDetailObject

/**
 *@author Hari Hara Sudhan. N
 */
interface ProductDetailDataSource {
    fun fetchProductById(id: String)
    fun getLifeCycleAwareListener(): MutableLiveData<ViewState<ProductDetailObject>>
}