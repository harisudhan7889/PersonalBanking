package au.com.nab.framework

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Hari Hara Sudhan. N
 */
@Entity(tableName = "products")
data class ProductsEntity(
    @PrimaryKey val productId: String,
    val productCategory: String, val name: String,
    val description: String, val brand: String,
    val isTailored: Boolean = false,
    @Embedded val additionalInformation: AdditionalInformation
)

data class AdditionalInformation(
    val overviewUri: String, val termsUri: String,
    val eligibilityUri: String, val feesAndPricingUri: String
)