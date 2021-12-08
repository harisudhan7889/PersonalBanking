package au.com.nab.di

import au.com.nab.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Hari Hara Sudhan. N
 */
@Module(includes = [ConvertorModule::class])
@InstallIn(SingletonComponent::class)
object ProductClientModule {

    @Provides
    @Singleton
    fun retrofit(httpClient: OkHttpClient,
                 gsonConverterFactory: GsonConverterFactory,
                 rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAB_BASE_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun client(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {
            var request = it.request()
            request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("x-v", "3")
                .build()
            it.proceed(request)
        }.build()
    }
}