package au.com.nab.di.productdetail

import au.com.nab.framework.ProductsDao
import au.com.nab.framework.productdetail.ProductDetailRepository
import au.com.nab.framework.productdetail.ProductDetailApi
import au.com.nab.framework.productdetail.ProductDetailSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides the required repository with data source implementation object.
 *
 * @see ProductDetailApiModule - Has methods to fetch the detail of a product
 * @author Hari Hara Sudhan. N
 */
@Module(includes = [ProductDetailApiModule::class])
@InstallIn(SingletonComponent::class)
object ProductDetailRepoModule {

    @Provides
    @Singleton
    fun provideProductDetailRepository(productDetailDataSource: ProductDetailSourceImpl): ProductDetailRepository {
        return ProductDetailRepository(
            productDetailDataSource
        )
    }

    @Provides
    @Singleton
    fun provideProductDetailDataSource(productDetailApi: ProductDetailApi,
                                       productsDao: ProductsDao): ProductDetailSourceImpl {
        return ProductDetailSourceImpl(productDetailApi, productsDao)
    }
}