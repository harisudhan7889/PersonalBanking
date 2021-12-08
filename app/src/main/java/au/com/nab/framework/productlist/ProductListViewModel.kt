package au.com.nab.framework.productlist


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.nab.domain.ViewState
import au.com.nab.domain.productlist.ProductObject
import au.com.nab.data.productlist.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
@HiltViewModel
class ProductListViewModel @Inject constructor(val productListRepository: ProductListRepository): ViewModel() {
    fun execute() = productListRepository.fetchProducts()
    fun getProductsListener(): MutableLiveData<ViewState<ProductObject>> = productListRepository.getLifeCycleAwareListener()
}