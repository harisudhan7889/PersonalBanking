package au.com.nab.framework

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Hari Hara Sudhan. N
 */
@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(products: List<ProductsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductFees(fees: List<Fee>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductFeatures(features: List<Feature>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductEligibilities(eligibilityList: List<Eligibility>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductLendingRate(lendingRates: List<LendingRate>)

    @Query("Select * from products")
    fun readAllProducts(): LiveData<List<ProductsEntity>>

    @Update
    fun updateProduct(product: ProductsEntity)

    @Transaction
    fun insertProductDetails(product: ProductsEntity, fees: List<Fee>,
                             features: List<Feature>,
                             eligibilityList: List<Eligibility>,
                             lendingRates: List<LendingRate>) {
        updateProduct(product)
        insertProductFees(fees)
        insertProductFeatures(features)
        insertProductEligibilities(eligibilityList)
        insertProductLendingRate(lendingRates)
    }

    @Transaction
    @Query("Select * from products WHERE productId = :productId")
    fun readProductById(productId: String): LiveData<ProductData>
}