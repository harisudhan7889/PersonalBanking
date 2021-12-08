package au.com.nab.data.productlist

import au.com.nab.domain.productlist.ProductObject
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
class ProductListRepository @Inject constructor(val productListDataSource: ProductListDataSource) {
    fun fetchProducts() = productListDataSource.fetchProducts()
    fun saveProducts(productObject: ProductObject) = productListDataSource.saveProducts(productObject)
    fun getLifeCycleAwareListener() = productListDataSource.getLifeCycleAwareListener()
}