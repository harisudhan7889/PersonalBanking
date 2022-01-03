package au.com.nab.framework.productdetail

import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.framework.DbProductEncapsuler
import javax.inject.Inject

/**
 * Repository class that mediates
 * between Presentation and Data Source Implementation(Remote or Local).
 *
 * @author Hari Hara Sudhan. N
 */
class ProductDetailRepository @Inject constructor(val productDetailDataSource
                                                  : ProductDetailDataSource<DbProductEncapsuler>) {
    fun fetchProductById(productId: String) = productDetailDataSource.fetchProductById(productId)
    fun getObserver() = productDetailDataSource.getObserver()
    fun onCleared() = productDetailDataSource.onCleared()
    fun cacheProduct(product: DbProductEncapsuler) = productDetailDataSource.cache(product)
}