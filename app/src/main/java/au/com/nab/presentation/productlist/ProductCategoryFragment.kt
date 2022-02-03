package au.com.nab.presentation.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import au.com.nab.R
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.LoadingState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.RxBus
import au.com.nab.framework.productlist.ProductListViewModel
import au.com.nab.presentation.BaseRecyclerAdapter
import au.com.nab.presentation.BaseRecyclerDataBinder
import au.com.nab.presentation.ProductsWrapper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_category.*

/**
 * @author Hari Hara Sudhan. N
 */
class ProductCategoryFragment : Fragment() {

    private val productListViewModel: ProductListViewModel by hiltNavGraphViewModels(R.id.navigation_products)
    private var productsCategory: Map<String, List<ProductsEntity>>? = null

    private val bindInterface = object: BaseRecyclerDataBinder<String> {
        override fun bind(position: Int, item: String, view: View) {

            // Since there is no thumbnail
            // and productCategoryName is not kind of label,
            // this UI specific logic is added.
            val(productCategoryName, productCategoryThumbnail) = ProductCategory.getProductCategoryObject(item)

            view.findViewById<ImageView>(R.id.imgItem).apply {
                Glide.with(this@ProductCategoryFragment)
                    .load(productCategoryThumbnail)
                    .centerCrop()
                    .into(this)
            }
            view.findViewById<TextView>(R.id.productCategory).text = productCategoryName
            view.setOnClickListener {
                productsCategory?.get(item)?.let {
                    RxBus.publish(ProductsWrapper(it))
                    findNavController().navigate(R.id.action_product_category_to_products)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productListViewModel.getGroupedProductsObserver().observe(this,
            Observer<ViewState<Map<String, List<ProductsEntity>>>> {
                when (it) {
                    is LoadingState -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is DataState -> {
                        it.data.run {
                            productsCategory = this
                            val adapter =  BaseRecyclerAdapter<String>(ArrayList(keys), R.layout.item_product_category, bindInterface)
                            productCategoryGrid.adapter = adapter
                        }
                        progressBar.visibility = View.GONE
                    }
                    is Error -> {
                        progressBar.visibility = View.GONE
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_category, container, false)
    }

    override fun onStart() {
        super.onStart()
        productListViewModel.execute()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productCategoryGrid.layoutManager = GridLayoutManager(context, 1)
    }
}