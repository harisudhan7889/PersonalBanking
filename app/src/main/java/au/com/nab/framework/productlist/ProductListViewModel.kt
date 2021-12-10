package au.com.nab.framework.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
@HiltViewModel
class ProductListViewModel @Inject constructor(val productListRepository: ProductListRepository): ViewModel() {
    fun execute() = productListRepository.fetchProducts()
    fun getProductListObserver(): MutableLiveData<ViewState<List<ProductsEntity>>> = productListRepository.getObserver()
    override fun onCleared() = productListRepository.onCleared()
}