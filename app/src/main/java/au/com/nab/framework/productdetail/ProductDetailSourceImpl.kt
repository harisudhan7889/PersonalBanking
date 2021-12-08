package au.com.nab.framework.productdetail

import androidx.lifecycle.MutableLiveData
import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.domain.DataState
import au.com.nab.domain.ErrorState
import au.com.nab.domain.LoadingState
import au.com.nab.domain.ViewState
import au.com.nab.domain.productdetail.ProductDetailObject
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
class ProductDetailSourceImpl @Inject constructor(val productDetailApi: ProductDetailApi): ProductDetailDataSource {

    private val productDetailListener = MutableLiveData<ViewState<ProductDetailObject>>()

    override fun fetchProductById(id: String) {
        productDetailApi.getProductById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<ProductDetailObject> {
                override fun onSuccess(productDetail: ProductDetailObject) {
                    productDetailListener.value = DataState(productDetail)
                }

                override fun onSubscribe(disposable: Disposable) {
                    productDetailListener.value = LoadingState(null)
                }

                override fun onError(error: Throwable) {
                    productDetailListener.value = ErrorState(error, null)
                }

            })
    }

    override fun getLifeCycleAwareListener() = productDetailListener
}