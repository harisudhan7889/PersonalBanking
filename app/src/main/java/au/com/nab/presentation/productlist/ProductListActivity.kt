package au.com.nab.presentation.productlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import au.com.nab.R
import au.com.nab.domain.DataState
import au.com.nab.domain.LoadingState
import au.com.nab.domain.ViewState
import au.com.nab.domain.productlist.ProductObject
import au.com.nab.framework.productlist.ProductListViewModel
import au.com.nab.presentation.productdetail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product_list.*

/**
 * @author Hari Hara Sudhan. N
 */
@AndroidEntryPoint
class ProductListActivity: AppCompatActivity() {

    private val productListViewModel: ProductListViewModel by viewModels()

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ProductListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        productDetail.setOnClickListener {
            startActivity(ProductDetailActivity.getIntent(this, "084a4d49-6285-4f9d-911d-02a728cc3337"))
        }
        productListViewModel.execute()
        productListViewModel.getProductsListener().observe(this,
            Observer<ViewState<ProductObject>> {
                when (it) {
                    is LoadingState -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is DataState -> {
                        it.data.run {
                            Log.d("Products Count", "${data.products?.size ?: 0}")
                            productDetail.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }
                    is Error -> {
                        progressBar.visibility = View.GONE
                    }
                }
            })
    }
}