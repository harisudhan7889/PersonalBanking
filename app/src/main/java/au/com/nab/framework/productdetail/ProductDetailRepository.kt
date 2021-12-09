package au.com.nab.framework.productdetail

import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.domain.ViewState
import au.com.nab.framework.ProductsEntity
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
class ProductDetailRepository @Inject constructor(val productDetailDataSource
                                                  : ProductDetailDataSource<ViewState<List<ProductsEntity>>>) {
    fun fetchProductById(productId: String) = productDetailDataSource.fetchProductById(productId)
    fun getObserver() = productDetailDataSource.getObserver()
    fun onCleared() = productDetailDataSource.onCleared()
}