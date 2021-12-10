package au.com.nab.framework.productdetail

import au.com.nab.domain.productdetail.ProductDetailObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Provide method to get detail of a product.
 *
 * @author Hari Hara Sudhan. N
 */
interface ProductDetailApi {
    @GET("/cds-au/v1/banking/products/{productId}")
    fun getProductById(@Path("productId") productId: String): Single<ProductDetailObject>
}