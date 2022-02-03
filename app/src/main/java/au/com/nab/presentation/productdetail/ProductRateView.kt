package au.com.nab.presentation.productdetail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.nab.R
import au.com.nab.framework.LendingRate
import au.com.nab.presentation.BaseRecyclerAdapter
import au.com.nab.presentation.BaseRecyclerDataBinder
import kotlinx.android.synthetic.main.view_product_info.view.*

/**
 * @author Hari Hara Sudhan. N
 */
class ProductRateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes)  {

    private val bindInterface = object: BaseRecyclerDataBinder<LendingRate> {
        override fun bind(position: Int, item: LendingRate, view: View) {
            view.findViewById<TextView>(R.id.rateTypeLabel).text = item.lendingRateType
            view.findViewById<TextView>(R.id.comparisonRateValue).text = if (item.comparisonRate.isNotEmpty()) item.comparisonRate else "-"
            view.findViewById<TextView>(R.id.interestRateValue).text = if (item.rate.isNotEmpty()) item.rate else "-"
        }
    }

    init {
        View.inflate(context, R.layout.view_product_info, this)
        productInfoContainer.layoutManager = LinearLayoutManager(context)
    }

    fun bindData(rates: List<LendingRate>) {
        val adapter =  BaseRecyclerAdapter(rates as ArrayList<LendingRate>, R.layout.widget_rate_text, bindInterface)
        productInfoLabel.text = resources.getText(R.string.rate_label)
        productInfoContainer.adapter = adapter
    }
}