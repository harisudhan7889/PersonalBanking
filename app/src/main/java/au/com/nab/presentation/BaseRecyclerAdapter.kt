package au.com.nab.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Hari Hara Sudhan. N
 */
class BaseRecyclerAdapter<T : Any>(
    private val dataSet: ArrayList<T>,
    @LayoutRes val layoutRes: Int,
    private val bindInterface: BaseRecyclerDataBinder<T>
) : RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, dataSet[position], bindInterface)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun <T : Any> bind(position: Int, item: T, bindInterface: BaseRecyclerDataBinder<T>) =
            bindInterface.bind(position, item, view)
    }

}