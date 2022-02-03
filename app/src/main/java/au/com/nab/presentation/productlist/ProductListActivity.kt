package au.com.nab.presentation.productlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import au.com.nab.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product_list.*

/**
 * @author Hari Hara Sudhan. N
 */
@AndroidEntryPoint
class ProductListActivity: AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ProductListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        initializeToolbar()
    }

    private fun initializeToolbar() {
        toolbar.title = resources.getText(R.string.nab_product_list_title)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}