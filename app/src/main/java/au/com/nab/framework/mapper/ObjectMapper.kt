package au.com.nab.framework.mapper

import au.com.nab.domain.common.Product
import au.com.nab.framework.AdditionalInformation
import au.com.nab.framework.ProductsEntity

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
        return ProductsEntity(
            remoteProduct.productId.orEmpty(),
            remoteProduct.productCategory.orEmpty(), remoteProduct.name.orEmpty(),
            remoteProduct.description.orEmpty(), remoteProduct.brand.orEmpty(),
            remoteProduct.isTailored ?: false, mapRemoteAdditionalInfo(remoteProduct.additionalInformation)
        )
    }

    private fun mapRemoteAdditionalInfo(remoteAdditionalInfo: au.com.nab.domain.common.AdditionalInformation?): AdditionalInformation {
        return AdditionalInformation(
            remoteAdditionalInfo?.overviewUri.orEmpty(),
            remoteAdditionalInfo?.termsUri.orEmpty(),
            remoteAdditionalInfo?.eligibilityUri.orEmpty(),
            remoteAdditionalInfo?.feesAndPricingUri.orEmpty()
        )
    }
}