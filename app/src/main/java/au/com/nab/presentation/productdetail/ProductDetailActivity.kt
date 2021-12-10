package au.com.nab.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import au.com.nab.R
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ErrorState
import au.com.nab.domain.common.LoadingState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.productdetail.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product_detail.*

/**
 * @author Hari Hara Sudhan. N
 */

@AndroidEntryPoint
class ProductDetailActivity: AppCompatActivity() {

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
        fun getIntent(context: Context, productId: String?): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT_ID, productId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        val productId = intent.getStringExtra(PRODUCT_ID)
        productDetailViewModel.execute(productId)
        productDetailViewModel.getProductDetailListener().observe(this,
            Observer<ViewState<ProductsEntity>> {
                when (it) {
                    is LoadingState -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is DataState -> {
                        it.data.run {
                            progressBar.visibility = View.GONE
                            productName.visibility = View.VISIBLE
                            productName.text = name
                        }
                    }

                    is ErrorState -> {
                        progressBar.visibility = View.GONE
                        productName.visibility = View.VISIBLE
                        it.error.run {
                            productName.text = message
                        }
                    }
                }
            })
    }
}