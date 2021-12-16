package au.com.nab.framework.productdetail

import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductData
import javax.inject.Inject

/**
 * Repository class that mediates
 * between Presentation and Data Source Implementation(Remote or Local).
 *
 * @author Hari Hara Sudhan. N
 */
class ProductDetailRepository @Inject constructor(val productDetailDataSource
                                                  : ProductDetailDataSource<ViewState<ProductData>>) {
    fun fetchProductById(productId: String) = productDetailDataSource.fetchProductById(productId)
    fun getObserver() = productDetailDataSource.getObserver()
    fun onCleared() = productDetailDataSource.onCleared()
}