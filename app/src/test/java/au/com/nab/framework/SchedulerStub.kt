package au.com.nab.framework

import au.com.nab.framework.scheduler.BaseScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * @author Hari Hara Sudhan. N
 */
class SchedulerStub: BaseScheduler {
    override fun getIoScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getTrampolineScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getComputationScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getNewScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getMainThread(): Scheduler {
        return Schedulers.trampoline()
    }
}