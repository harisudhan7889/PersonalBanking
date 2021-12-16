package au.com.nab.presentation

import android.view.View

/**
 * @author Hari Hara Sudhan. N
 */
interface BaseRecyclerDataBinder<T> {
   fun bind(position: Int, item: T, view: View)
}