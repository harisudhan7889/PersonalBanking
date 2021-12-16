package au.com.nab

import androidx.room.Database
import androidx.room.RoomDatabase
import au.com.nab.framework.*

/**
 * @author Hari Hara Sudhan. N
 */
@Database(entities = [ProductsEntity::class, Feature::class,
    Fee::class, Eligibility::class, LendingRate::class], version = 1)
abstract class BankingDatabase: RoomDatabase() {
    abstract fun getProductsDao(): ProductsDao
}