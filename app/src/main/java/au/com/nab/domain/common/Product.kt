package au.com.nab.domain.common

/**
 * @author Hari Hara Sudhan.N
 */
data class Product(
    val productId: String?, val lastUpdated: String?,
    val productCategory: String?, val name: String?,
    val description: String?, val brand: String?,
    val isTailored: Boolean? = false, val additionalInformation: AdditionalInformation?,
    val features: List<Feature>?, val constraints: List<Constraint>?,
    val eligibility: List<Eligibility>,
    val fees: List<Fee>?, val lendingRates: List<LendingRate>?
)

data class AdditionalInformation(
    val overviewUri: String?, val termsUri: String?,
    val eligibilityUri: String?, val feesAndPricingUri: String?
)

data class Feature(val featureType: String?, val additionalInfo: String?)

data class Constraint(val constraintType: String?, val additionalValue: String?)

data class Eligibility(val eligibilityType: String?, val additionalValue: String?)

data class Fee(val name: String?, val feeType: String?, val amount: String?,
               val balanceRate: String?, val transactionRate: String?,
               val accruedRate: String?, val currency: String?, val additionalInfoUri: String?)

data class LendingRate(val lendingRateType: String?, val rate: String?, val comparisonRate: String?,
                       val calculationFrequency: String?, val applicationFrequency: String?,
                       val interestPaymentDue: String?, val additionalInfoUri: String?)