package au.com.nab.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.com.nab.R
import au.com.nab.presentation.productlist.ProductListActivity
import kotlinx.android.synthetic.main.activity_launch_screen.*

/**
 * @author Hari Hari Sudhan. N
 */
class LaunchScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)
        initiateEvent.setOnClickListener {
            startActivity(ProductListActivity.getIntent(this))
        }
    }
}
