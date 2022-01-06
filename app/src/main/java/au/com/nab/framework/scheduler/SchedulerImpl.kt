package au.com.nab.framework.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerImpl: BaseScheduler {
    override fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun getComputationScheduler(): Scheduler {
        return Schedulers.computation()
    }

    override fun getTrampolineScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getNewScheduler(): Scheduler {
        return Schedulers.newThread()
    }

    override fun getMainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}