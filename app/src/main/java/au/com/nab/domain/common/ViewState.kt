package au.com.nab.domain.common

/**
 * Wrapper state object used to wrap the success, error and subscribe results
 * for view's consumption.
 *
 * @author Hari Hara Sudhan.N
 */
sealed class ViewState<T> {
    abstract val data: T?
}
data class LoadingState<T>(override val data: T?): ViewState<T>()
data class DataState<T>(override val data: T): ViewState<T>()
data class ErrorState<T>(val error: Throwable, override val data: T?): ViewState<T>()