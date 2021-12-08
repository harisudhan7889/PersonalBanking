package au.com.nab.di.productlist

import au.com.nab.framework.productlist.ProductListDataSourceImpl
import au.com.nab.framework.productlist.ProductListApi
import au.com.nab.data.productlist.ProductListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Hari Hara Sudhan. N
 */
@Module(includes = [ProductListApiModule::class])
@InstallIn(SingletonComponent::class)
object ProductListRepositoryModule {

    @Provides
    @Singleton
    fun provideProductListRepository(productListDataSourceImpl: ProductListDataSourceImpl): ProductListRepository {
        return ProductListRepository(
            productListDataSourceImpl
        )
    }

    @Provides
    @Singleton
    fun provideProductListSource(productListApi: ProductListApi): ProductListDataSourceImpl {
        return ProductListDataSourceImpl(
            productListApi
        )
    }
}