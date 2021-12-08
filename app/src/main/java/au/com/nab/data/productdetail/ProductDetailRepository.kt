package au.com.nab.data.productdetail

import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
class ProductDetailRepository @Inject constructor(val productDetailDataSource: ProductDetailDataSource) {
    fun fetchProductById(productId: String) = productDetailDataSource.fetchProductById(productId)
    fun getLifeCycleAwareListener() = productDetailDataSource.getLifeCycleAwareListener()
}