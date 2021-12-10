package au.com.nab.framework.productlist

import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsEntity
import javax.inject.Inject

/**
 * Repository class that mediates
 * between Presentation and Data Source Implementation(Remote or Local).
 *
 * @author Hari Hara Sudhan. N
 */
class ProductListRepository @Inject constructor(val productListDataSource
                                                : ProductListDataSource<ViewState<List<ProductsEntity>>>
) {
    fun fetchProducts() = productListDataSource.fetchProducts()
    fun getObserver() = productListDataSource.getObserver()
    fun onCleared() = productListDataSource.onCleared()
}