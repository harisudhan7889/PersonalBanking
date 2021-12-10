package au.com.nab.di.productlist

import au.com.nab.framework.productlist.ProductListDataSourceImpl
import au.com.nab.framework.productlist.ProductListApi
import au.com.nab.framework.productlist.ProductListRepository
import au.com.nab.framework.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides the required repository with data source implementation object.
 *
 * @see ProductListApiModule - Has methods to fetch all the products.
 * @author Hari Hara Sudhan. N
 */
@Module(includes = [ProductListApiModule::class])
@InstallIn(SingletonComponent::class)
object ProductListRepoModule {

    @Provides
    @Singleton
    fun provideProductListRepository(productListDataSourceImpl: ProductListDataSourceImpl): ProductListRepository {
        return ProductListRepository(
            productListDataSourceImpl
        )
    }

    @Provides
    @Singleton
    fun provideProductListSource(productListApi: ProductListApi,
                                 productsDao: ProductsDao): ProductListDataSourceImpl {
        return ProductListDataSourceImpl(
            productListApi, productsDao
        )
    }
}