package au.com.nab.data.productlist

import androidx.lifecycle.MutableLiveData
import au.com.nab.domain.ViewState
import au.com.nab.domain.productlist.ProductObject

/**
 *@author Hari Hara Sudhan. N
 */
interface ProductListDataSource {
    fun fetchProducts()
    fun saveProducts(productObject: ProductObject)
    fun getLifeCycleAwareListener(): MutableLiveData<ViewState<ProductObject>>
}