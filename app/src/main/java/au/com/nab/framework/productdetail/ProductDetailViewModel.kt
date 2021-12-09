package au.com.nab.framework.productdetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(val productDetailRepository: ProductDetailRepository): ViewModel() {
    fun execute(productId: String) = productDetailRepository.fetchProductById(productId)
    fun getProductDetailListener() = productDetailRepository.getObserver()
    override fun onCleared() = productDetailRepository.onCleared()
}