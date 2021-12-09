package au.com.nab.data.productlist

import au.com.nab.data.DataSource

/**
 *@author Hari Hara Sudhan. N
 */
interface ProductListDataSource<O>: DataSource<O> {
     fun fetchProducts()
}