package au.com.nab.di.productdetail

import au.com.nab.di.ProductClientModule
import au.com.nab.framework.productdetail.ProductDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Hari Hara Sudhan. N
 */
@Module(includes = [ProductClientModule::class])
@InstallIn(SingletonComponent::class)
object ProductDetailApiModule {
    @Provides
    @Singleton
    fun provideProductDetailApi(retrofit: Retrofit): ProductDetailApi {
        return retrofit.create(ProductDetailApi::class.java)
    }
}