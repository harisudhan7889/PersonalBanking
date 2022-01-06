package au.com.nab.di

import au.com.nab.framework.scheduler.BaseScheduler
import au.com.nab.framework.scheduler.SchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Hari Hara Sudhan. N
 */
@InstallIn(SingletonComponent::class)
@Module
object SchedulersModule {
    @Provides
    @Singleton
    fun provideBackgroundScheduler(): BaseScheduler {
        return SchedulerImpl()
    }
}