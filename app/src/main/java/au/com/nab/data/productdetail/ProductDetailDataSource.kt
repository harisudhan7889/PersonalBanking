package au.com.nab.data.productdetail

import au.com.nab.data.DataSource

/**
 *@author Hari Hara Sudhan. N
 */
interface ProductDetailDataSource<O> : DataSource<O> {
    /**
     * Can fetch product with the Product Id.
     * It does not return object as this should be
     * an asynchronous background task.
     *
     * Fetching can be either remote network
     * or local database call.
     *
     * @param id - Product Id
     */
    fun fetchProductById(id: String)
}