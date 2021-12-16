package au.com.nab.framework

import androidx.room.*

/**
 * @author Hari Hara Sudhan. N
 */
@Entity(tableName = "products")
data class ProductsEntity (
    @PrimaryKey val productId: String,
    val productCategory: String, val name: String,
    val description: String, val brand: String,
    val isTailored: Boolean = false
)

@Entity(tableName = "product_features")
data class Feature(@PrimaryKey val id: String, val productId: String,
                   val featureType: String, val additionalInfo: String, val additionalValue: String)

@Entity(tableName = "product_eligibility")
data class Eligibility(@PrimaryKey val id: String, val productId: String, val eligibilityType: String, val additionalValue: String, val additionalInfo: String)

@Entity(tableName = "product_fee")
data class Fee(@PrimaryKey val id: String, val productId: String, val name: String, val feeType: String, val amount: String,
               val balanceRate: String, val transactionRate: String,
               val accruedRate: String, val currency: String)

@Entity(tableName = "product_lending_rate")
data class LendingRate(@PrimaryKey val id: String, val productId: String, val lendingRateType: String, val rate: String, val comparisonRate: String,
                       val interestPaymentDue: String, val additionalValue: String, val additionalInfo: String)