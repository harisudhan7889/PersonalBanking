package au.com.nab.framework.utility

import au.com.nab.domain.common.Product
import au.com.nab.framework.*

/**
 * Mapper util to map Remote DTO to Local DB DTO.
 *
 * @author Hari Hara Sudhan. N
 */
object ObjectMapper {

    // Nullable to Non-nullable
    inline fun <I, O> mapNullInputList(input: List<I>?, mapSingle: (I) -> O): List<O> {
        return input?.map { mapSingle(it) } ?: emptyList()
    }

    inline fun mapRemoteProducts(
        remoteProducts: List<Product>?,
        mapRemoteProducts: (List<Product>?) -> List<ProductsEntity>
    ): List<ProductsEntity> {
        return mapRemoteProducts(remoteProducts)
    }

    fun mapRemoteProduct(remoteProduct: Product): ProductsEntity {
        val productId = remoteProduct.productId.orEmpty()
        return ProductsEntity(
            productId,
            remoteProduct.productCategory.orEmpty(), remoteProduct.name.orEmpty(),
            remoteProduct.description.orEmpty(), remoteProduct.brand.orEmpty(),
            remoteProduct.isTailored ?: false)
    }

    private fun mapRemoteProductFeatures(remoteProductFeatures: List<au.com.nab.domain.common.Feature>?,
                                         mapRemoteProductFeatures: (List<au.com.nab.domain.common.Feature>?) -> List<Feature>): List<Feature> {

        return mapRemoteProductFeatures(remoteProductFeatures)
    }

    private fun mapRemoteFeature(productId: String, remoteFeature: au.com.nab.domain.common.Feature): Feature {
        return remoteFeature.run {
            Feature("$productId:feature:${featureType.orEmpty()}", productId, featureType.orEmpty(),
                additionalInfo.orEmpty(),
                additionalValue.orEmpty())
        }
    }

    private fun mapRemoteProductFees(remoteProductFees: List<au.com.nab.domain.common.Fee>?,
                                     mapRemoteProductFees: (List<au.com.nab.domain.common.Fee>?) -> List<Fee>): List<Fee> {

        return mapRemoteProductFees(remoteProductFees)
    }

    private fun mapRemoteFee(productId: String, remoteFee: au.com.nab.domain.common.Fee): Fee {
        return remoteFee.run {
            Fee("$productId:fee:$feeType", productId, name.orEmpty(),
                feeType.orEmpty(),
                amount.orEmpty(),
                balanceRate.orEmpty(),
                transactionRate.orEmpty(),
                accruedRate.orEmpty(),
                currency.orEmpty()
            )
        }
    }

    private fun mapRemoteProductEligibility(
        remoteProductEligibility: List<au.com.nab.domain.common.Eligibility>?,
        mapRemoteProductEligibility: (List<au.com.nab.domain.common.Eligibility>?) -> List<Eligibility>
    ): List<Eligibility> {

        return mapRemoteProductEligibility(remoteProductEligibility)
    }

    private fun mapRemoteEligibility(productId: String, remoteEligibility: au.com.nab.domain.common.Eligibility): Eligibility {
        return remoteEligibility.run {
            Eligibility("$productId:eligibility:${eligibilityType.orEmpty()}", productId, eligibilityType.orEmpty(),
                additionalValue.orEmpty(), additionalInfo.orEmpty())
        }
    }

    private fun mapRemoteProductLendingRate(
        remoteProductLendingRates: List<au.com.nab.domain.common.LendingRate>?,
        mapRemoteProductLendingRates: (List<au.com.nab.domain.common.LendingRate>?) -> List<LendingRate>
    ): List<LendingRate> {
        return mapRemoteProductLendingRates(remoteProductLendingRates)
    }

    private fun mapRemoteLendingRate(productId: String, remoteLendingRate: au.com.nab.domain.common.LendingRate): LendingRate {
        return remoteLendingRate.run {
            LendingRate("$productId:rate:${lendingRateType.orEmpty()}", productId,
                lendingRateType.orEmpty(), rate.orEmpty(),
                comparisonRate.orEmpty(), interestPaymentDue.orEmpty(),
                additionalInfo.orEmpty(), additionalValue.orEmpty())
        }
    }

    fun mapRemoteToLocalProduct(product: Product): DbProductEncapsuler {
        val productObject = mapRemoteProduct(product)

        val productFeature =
            mapRemoteProductFeatures(product.features) { remoteFeatures ->
                mapNullInputList(remoteFeatures) { remoteFeature ->
                    mapRemoteFeature(productObject.productId, remoteFeature)
                }
            }

        val productFee = mapRemoteProductFees(product.fees) { remoteFees ->
            mapNullInputList(remoteFees) { remoteFee ->
                mapRemoteFee(productObject.productId, remoteFee)
            }
        }

        val productEligibility =
            mapRemoteProductEligibility(product.eligibility) { remoteEligibilities ->
                mapNullInputList(remoteEligibilities) { remoteEligibility ->
                    mapRemoteEligibility(productObject.productId, remoteEligibility)
                }
            }

        val productLendingRates =
            mapRemoteProductLendingRate(product.lendingRates) { remoteLendingRates ->
                mapNullInputList(remoteLendingRates) { remoteLendingRate ->
                    mapRemoteLendingRate(productObject.productId, remoteLendingRate)
                }
            }

        val productData = DbProductEncapsuler()
        productData.productsEntity = productObject
        productData.features = productFeature
        productData.fees = productFee
        productData.eligibility = productEligibility
        productData.lendingRate = productLendingRates

        return productData
    }

}