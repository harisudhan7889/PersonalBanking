package au.com.nab.presentation.productdetail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.nab.R
import au.com.nab.framework.Feature
import au.com.nab.presentation.BaseRecyclerAdapter
import au.com.nab.presentation.BaseRecyclerDataBinder
import kotlinx.android.synthetic.main.view_product_info.view.*

/**
 * @author Hari Hara Sudhan. N
 */
class ProductFeatureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes){

    private val bindInterface = object: BaseRecyclerDataBinder<Feature> {
        override fun bind(position: Int, item: Feature, view: View) {
            view.findViewById<TextView>(R.id.featureNumberLabel).text = "${position.plus(1)}"
            view.findViewById<TextView>(R.id.featureText).text =
                if (item.additionalValue.isNotEmpty())
                    item.additionalValue else item.additionalInfo
        }
    }

    init {
        View.inflate(context, R.layout.view_product_info, this)
        productInfoContainer.layoutManager = LinearLayoutManager(context)
    }

    fun bindData(features: List<Feature>) {
        features.filter {
            it.additionalInfo.isNotEmpty() || it.additionalValue.isNotEmpty()
        }.let {
            if (it.isNotEmpty()) {
                val adapter = BaseRecyclerAdapter(
                    it as ArrayList<Feature>,
                    R.layout.widget_feature_text,
                    bindInterface
                )
                productInfoLabel.text = resources.getText(R.string.feature_label)
                productInfoContainer.adapter = adapter
                horizontalLine.visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }
}