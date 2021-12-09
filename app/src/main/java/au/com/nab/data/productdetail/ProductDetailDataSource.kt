package au.com.nab.data.productdetail

import au.com.nab.data.DataSource

/**
 *@author Hari Hara Sudhan. N
 */
interface ProductDetailDataSource<O>: DataSource<O> {
    fun fetchProductById(id: String)
}