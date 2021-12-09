package au.com.nab.framework

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Hari Hara Sudhan. N
 */
@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<ProductsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: ProductsEntity)

    @Query("Select * from products")
    fun readAll(): LiveData<List<ProductsEntity>>

    @Update
    fun update(product: ProductsEntity)

    @Query("Select * from products WHERE productId = :productId")
    fun readById(productId: String): LiveData<ProductsEntity>

}