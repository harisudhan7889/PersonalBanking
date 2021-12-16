package au.com.nab.presentation.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import au.com.nab.R
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ErrorState
import au.com.nab.domain.common.LoadingState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductData
import au.com.nab.framework.productdetail.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.layout_detail_screen.*


/**
 * @author Hari Hara Sudhan. N
 */
@AndroidEntryPoint
class ProductDetailActivity: AppCompatActivity() {

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
        const val PRODUCT_NAME = "PRODUCT_NAME"
        fun getIntent(context: Context, productId: String?, productName: String?): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT_ID, productId)
            intent.putExtra(PRODUCT_NAME, productName)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_detail)

        val productId = intent.getStringExtra(PRODUCT_ID)
        val productName = intent.getStringExtra(PRODUCT_NAME)

        toolbar.title = productName

        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        productDetailViewModel.execute(productId)
        productDetailViewModel.getProductDetailListener().observe(this,
            Observer<ViewState<ProductData>>{
                when (it) {
                    is LoadingState -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is DataState -> {
                        it.data.run {
                            progressBar.visibility = View.GONE
                            bindDataToView()
                        }
                    }

                    is ErrorState -> {
                        progressBar.visibility = View.GONE

                    }
                }
            })
    }

    private fun ProductData.bindDataToView() {
        if (eligibility.isNotEmpty()) {
            eligibilityView.visibility = View.VISIBLE
            eligibilityView.bindData(eligibility)
        }
        if (features.isNotEmpty()) {
            featureView.visibility = View.VISIBLE
            featureView.bindData(features)
        }
        if (fees.isNotEmpty()) {
            feeView.visibility = View.VISIBLE
            feeView.bindData(fees)
        }
        if (lendingRate.isNotEmpty()) {
            rateView.visibility = View.VISIBLE
            rateView.bindData(lendingRate)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}