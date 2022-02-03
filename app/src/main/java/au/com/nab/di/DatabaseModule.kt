package au.com.nab.di

import android.app.Application
import androidx.room.Room
import au.com.nab.BankingDatabase
import au.com.nab.framework.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module class tha provides the database.
 *
 * @author Hari Hara Sudhan. N
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideProductDao(database: BankingDatabase): ProductsDao {
        return database.getProductsDao()
    }

    @Provides
    @Singleton
    fun provideBankingDatabase(context: Application): BankingDatabase {
        return synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                BankingDatabase::class.java,
                "banking_database"
            ).build()
        }
    }

}