package au.com.nab.di.productlist

import au.com.nab.di.ProductClientModule
import au.com.nab.framework.productlist.ProductListApi
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
object ProductListApiModule {
    @Provides
    @Singleton
    fun provideProductListApi(retrofit: Retrofit): ProductListApi {
        return retrofit.create(ProductListApi::class.java)
    }
}