package au.com.nab.di.productlist

import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.productlist.ProductListApi
import au.com.nab.framework.productlist.ProductListDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides the required repository with data source implementation object.
 *
 * @see ProductListApiModule - Has methods to fetch all the products.
 *
 * @author Hari Hara Sudhan. N
 */
@Module(includes = [ProductListApiModule::class])
@InstallIn(SingletonComponent::class)
object ProductListDataSourceModule {
    @Provides
    @Singleton
    fun provideProductListSource(
        productListApi: ProductListApi,
        productsDao: ProductsDao
    ): ProductListDataSource<List<ProductsEntity>> {
        return ProductListDataSourceImpl(
            productListApi, productsDao
        )
    }
}