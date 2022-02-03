package au.com.nab.presentation.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.nab.R
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.RxBus
import au.com.nab.presentation.BaseRecyclerAdapter
import au.com.nab.presentation.BaseRecyclerDataBinder
import au.com.nab.presentation.ProductsWrapper
import au.com.nab.presentation.productdetail.ProductDetailActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_products_group.*

/**
 * @author Hari Hara Sudhan. N
 */
class ProductsGroupFragment: Fragment() {

    private var groupedProducts: List<ProductsEntity>? = null
    private var disposable: Disposable?=null
    private val bindInterface = object: BaseRecyclerDataBinder<ProductsEntity>{
        override fun bind(position: Int, item: ProductsEntity, view: View) {
            view.findViewById<TextView>(R.id.productLabel).text = item.name
            view.findViewById<TextView>(R.id.productDescription).text = item.description
            view.findViewById<Button>(R.id.findOutMore).setOnClickListener {
                startActivity(ProductDetailActivity.getIntent(it.context, item.productId, item.name))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable = RxBus.listen(ProductsWrapper::class.java).subscribe {
            groupedProducts = it.products
            val adapter = BaseRecyclerAdapter(groupedProducts as ArrayList<ProductsEntity>, R.layout.item_product_group, bindInterface)
            groupedProductList.adapter = adapter
            groupedProductList.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable?.isDisposed != true) {
            disposable?.dispose()
        }
    }
}