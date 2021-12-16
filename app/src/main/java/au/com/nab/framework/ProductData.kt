package au.com.nab.framework

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Hari Hara Sudhan. N
 */
class ProductData {

    @Embedded lateinit var productsEntity: ProductsEntity

    @Relation(parentColumn = "productId", entityColumn = "productId")
    var features: List<Feature> = ArrayList()

    @Relation(parentColumn = "productId", entityColumn = "productId")
    var fees: List<Fee> = ArrayList()

    @Relation(parentColumn = "productId", entityColumn = "productId")
    var eligibility: List<Eligibility> = ArrayList()

    @Relation(parentColumn = "productId", entityColumn = "productId")
    var lendingRate: List<LendingRate> = ArrayList()

}