package au.com.nab.domain.productlist

/**
 *@author Hari Hara Sudhan. N
 */
data class ProductObject(val data: Data)

data class Data(val products: List<Product>?)

data class Product(
    val productId: String, val lastUpdated: String?,
    val productCategory: String?, val name: String?,
    val description: String?, val brand: String?,
    val isTailored: Boolean? = false, val additionalInformation: AdditionalInformation?
)

data class AdditionalInformation(
    val overviewUri: String?, val termsUri: String?,
    val eligibilityUri: String?, val feesAndPricingUri: String?
)