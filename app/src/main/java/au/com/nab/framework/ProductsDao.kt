package au.com.nab.framework

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * @author Hari Hara Sudhan. N
 */
@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(products: List<ProductsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductsEntity)

    @Query("Select * from products")
    fun readAllProducts(): LiveData<List<ProductsEntity>>

    @Update
    fun updateProduct(product: ProductsEntity)

    @Query("Select * from products WHERE productId = :productId")
    fun readProductById(productId: String): LiveData<ProductsEntity>

}