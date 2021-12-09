package au.com.nab

import androidx.room.Database
import androidx.room.RoomDatabase
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity

/**
 * @author Hari Hara Sudhan. N
 */
@Database(entities = [ProductsEntity::class], version = 1)
abstract class BankingDatabase: RoomDatabase() {
    abstract fun getProductsDao(): ProductsDao
}