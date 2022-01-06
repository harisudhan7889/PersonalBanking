package au.com.nab.framework.utility

import au.com.nab.domain.common.*
import org.junit.Assert
import org.junit.Test

/**
 * @author Hari Hara Sudhan. N
 */
class ObjectMapperTest {

    private val productId = "Product_1"

    @Test
    fun testRemoteProductToLocalProductWithNullProductId() {
        val product = Product()
        val productEntity = ObjectMapper.mapRemoteProduct(product)
        Assert.assertFalse(productEntity.productId == product.productId)
    }

    @Test
    fun testRemoteProductToLocalProductWithProductId() {
        val product = Product(productId)
        val productEntity = ObjectMapper.mapRemoteProduct(product)
        Assert.assertTrue(productEntity.productId == product.productId)
    }

    @Test
    fun testRemoteFeatureToLocalFeatureWithProductId() {
        val feature = Feature("FeatureType", null, null)
        val featureEntity = ObjectMapper.mapRemoteFeature(productId, feature)
        Assert.assertTrue(featureEntity.id == "$productId:feature:${feature.featureType.orEmpty()}")
        Assert.assertTrue(featureEntity.additionalValue.isEmpty())
    }

    @Test
    fun mapRemoteFeeToLocalFeeWithProductId() {
        val fee = Fee(null, "FeeType", null, null, null,
            null, null, null)
        val feeEntity = ObjectMapper.mapRemoteFee(productId, fee)
        Assert.assertTrue(feeEntity.id == "$productId:fee:${fee.feeType.orEmpty()}")
        Assert.assertTrue(feeEntity.name.isEmpty())
    }

    @Test
    fun mapRemoteRateToLocalRateWithProductId() {
        val rate = LendingRate("RateType", null, null, null, null,
            null, null, null, null)
        val rateEntity = ObjectMapper.mapRemoteLendingRate(productId, rate)
        Assert.assertTrue(rateEntity.id == "$productId:rate:${rate.lendingRateType.orEmpty()}")
        Assert.assertTrue(rateEntity.rate.isEmpty())
    }

    @Test
    fun mapRemoteEligibilityToLocalEligibilityWithProductId() {
        val eligibility = Eligibility("EligibilityType", null, null)
        val eligibilityEntity = ObjectMapper.mapRemoteEligibility(productId, eligibility)
        Assert.assertTrue(eligibilityEntity.id == "$productId:eligibility:${eligibility.eligibilityType.orEmpty()}")
        Assert.assertTrue(eligibilityEntity.additionalValue.isEmpty())
    }
}