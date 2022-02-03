package au.com.nab.presentation.productdetail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.nab.R
import au.com.nab.framework.Fee
import au.com.nab.presentation.BaseRecyclerAdapter
import au.com.nab.presentation.BaseRecyclerDataBinder
import kotlinx.android.synthetic.main.view_product_info.view.*
import kotlin.math.roundToInt

/**
 * @author Hari Hara Sudhan. N
 */
class ProductFeeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private val bindInterface = object: BaseRecyclerDataBinder<Fee> {
        override fun bind(position: Int, item: Fee, view: View) {
            view.findViewById<TextView>(R.id.feeName).text = item.name
            if(item.amount.isNotEmpty()) {
                view.findViewById<TextView>(R.id.feeValue).text =
                    resources.getString(R.string.fee_text, item.amount.toFloat().roundToInt(), item.currency)
            } else {
                view.findViewById<TextView>(R.id.feeValue).text = "-"
            }
        }
    }

    init {
        View.inflate(context, R.layout.view_product_info, this)
        productInfoContainer.layoutManager = LinearLayoutManager(context)
    }

    fun bindData(fees: List<Fee>) {
        val adapter =  BaseRecyclerAdapter(fees as ArrayList<Fee>, R.layout.widget_fee_text, bindInterface)
        productInfoLabel.text = resources.getText(R.string.fee_label)
        productInfoContainer.adapter = adapter
    }
}