package au.com.nab.presentation

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import au.com.nab.R
import au.com.nab.presentation.productlist.ProductListActivity

/**
 * @author Hari Hari Sudhan. N
 */
class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val WAIT_TIME = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            startActivity(ProductListActivity.getIntent(this))
            finish()
        }, WAIT_TIME)
    }
}
