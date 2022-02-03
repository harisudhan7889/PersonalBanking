package au.com.nab.framework.productlist

import au.com.nab.domain.productlist.ProductListObject
import io.reactivex.Single
import retrofit2.http.GET

/**
 * @author Hari Hara Sudhan. N
 */
interface ProductListApi {
    @GET("cds-au/v1/banking/products")
    fun getBankingProducts(): Single<ProductListObject>
}