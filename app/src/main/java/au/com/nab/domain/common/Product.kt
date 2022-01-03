package au.com.nab.domain.common

/**
 * @author Hari Hara Sudhan.N
 */
data class Product(
    val productId: String? = null, val lastUpdated: String? = null,
    val productCategory: String? = null, val name: String? = null,
    val description: String? = null, val brand: String? = null,
    val isTailored: Boolean? = false , val additionalInformation: AdditionalInformation? = null,
    val features: List<Feature>? = null, val constraints: List<Constraint>? = null,
    val eligibility: List<Eligibility>? = null,
    val fees: List<Fee>? = null, val lendingRates: List<LendingRate>? = null
)

data class AdditionalInformation(
    val overviewUri: String?, val termsUri: String?,
    val eligibilityUri: String?, val feesAndPricingUri: String?
)

data class Feature(val featureType: String?, val additionalInfo: String?, val additionalValue: String?)

data class Constraint(val constraintType: String?, val additionalValue: String?)

data class Eligibility(val eligibilityType: String?, val additionalValue: String?, val additionalInfo: String?)

data class Fee(val name: String?, val feeType: String?, val amount: String?,
               val balanceRate: String?, val transactionRate: String?,
               val accruedRate: String?, val currency: String?, val additionalInfoUri: String?)

data class LendingRate(val lendingRateType: String?, val rate: String?, val comparisonRate: String?,
                       val calculationFrequency: String?, val applicationFrequency: String?,
                       val interestPaymentDue: String?, val additionalInfoUri: String?,
                       val additionalInfo: String?, val additionalValue: String?)