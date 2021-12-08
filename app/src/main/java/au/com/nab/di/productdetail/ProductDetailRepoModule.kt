package au.com.nab.di.productdetail

import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.data.productdetail.ProductDetailRepository
import au.com.nab.framework.productdetail.ProductDetailApi
import au.com.nab.framework.productdetail.ProductDetailSourceImpl
import au.com.nab.framework.productlist.ProductListDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Hari Hara Sudhan. N
 */
@Module(includes = [ProductDetailApiModule::class])
@InstallIn(SingletonComponent::class)
object ProductDetailRepoModule {

    @Provides
    @Singleton
    fun provideProductDetailRepository(productDetailDataSource: ProductDetailSourceImpl): ProductDetailRepository {
        return ProductDetailRepository(productDetailDataSource)
    }

    @Provides
    @Singleton
    fun provideProductDetailDataSource(productDetailApi: ProductDetailApi): ProductDetailSourceImpl {
        return ProductDetailSourceImpl(productDetailApi)
    }
}