package au.com.nab.domain

/**
 * @author Hari Hara Sudhan.N
 */
sealed class ViewState<T> {
    abstract val data: T?
}
data class LoadingState<T>(override val data: T?): ViewState<T>()
data class DataState<T>(override val data: T): ViewState<T>()
data class ErrorState<T>(val error: Throwable, override val data: T?): ViewState<T>()