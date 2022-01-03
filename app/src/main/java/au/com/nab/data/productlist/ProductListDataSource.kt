package au.com.nab.data.productlist

import au.com.nab.data.DataSource
import io.reactivex.Single

/**
 *@author Hari Hara Sudhan. N
 */
interface ProductListDataSource<O>: DataSource<O> {
     /**
      * Can fetch all products.
      * It does not return object as this should be
      * an asynchronous background task.
      *
      * Fetching can be either remote network
      * or local database call.
      */
     fun fetchProducts(): Single<O>
}