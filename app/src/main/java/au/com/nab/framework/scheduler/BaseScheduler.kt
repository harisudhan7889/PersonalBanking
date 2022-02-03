package au.com.nab.framework.scheduler

import io.reactivex.Scheduler

/**
 * @author Hari Hara Sudhan. N
 */
interface BaseScheduler {
    fun getIoScheduler(): Scheduler
    fun getTrampolineScheduler(): Scheduler
    fun getComputationScheduler(): Scheduler
    fun getNewScheduler(): Scheduler
    fun getMainThread(): Scheduler
}