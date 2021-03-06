package au.com.nab.framework.productlist

import au.com.nab.framework.ProductsEntity
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
class ProductListGroup @Inject constructor() {
    fun groupProductsByCategory(products: List<ProductsEntity>): Map<String,
            List<ProductsEntity>> {
        return products.groupBy {
            it.productCategory
        }
    }
}