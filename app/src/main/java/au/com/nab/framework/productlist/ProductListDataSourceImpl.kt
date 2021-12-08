package au.com.nab.framework.productlist

import androidx.lifecycle.MutableLiveData
import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.domain.DataState
import au.com.nab.domain.ErrorState
import au.com.nab.domain.LoadingState
import au.com.nab.domain.ViewState
import au.com.nab.domain.productlist.ProductObject
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Framework layer where implementation will be happen
 *
 * @author Hari Hara Sudhan. N
 */
class ProductListDataSourceImpl @Inject constructor(val productListApi: ProductListApi):
    ProductListDataSource {

    private val productsListener = MutableLiveData<ViewState<ProductObject>>()

    override fun fetchProducts() {
        productListApi.getBankingProducts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductObject> {
                override fun onSuccess(response: ProductObject) {
                    productsListener.value =
                        DataState(response)
                }

                override fun onError(error: Throwable) {
                    productsListener.value =
                        ErrorState(error, null)
                }

                override fun onSubscribe(disposable: Disposable) {
                    productsListener.value = LoadingState(null)
                }
            })
    }

    override fun saveProducts(productObject: ProductObject) {

    }

    override fun getLifeCycleAwareListener() = productsListener
}