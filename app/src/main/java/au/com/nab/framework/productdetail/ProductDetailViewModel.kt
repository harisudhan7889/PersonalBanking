package au.com.nab.framework.productdetail

import androidx.lifecycle.ViewModel
import au.com.nab.domain.common.ErrorState
import au.com.nab.domain.common.LoadingState
import au.com.nab.framework.DbProductEncapsuler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(val productDetailRepository: ProductDetailRepository) :
    ViewModel() {
    fun execute(productId: String) {
        productDetailRepository.fetchProductById(productId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<DbProductEncapsuler> {

                override fun onSubscribe(disposable: Disposable) {
                    getProductDetailListener().value = LoadingState(null)
                }

                override fun onError(error: Throwable) {
                    getProductDetailListener().value = ErrorState(error, null)
                }

                override fun onSuccess(product: DbProductEncapsuler) {
                    productDetailRepository.cacheProduct(product)
                }

            })
    }

    fun getProductDetailListener() = productDetailRepository.getObserver()

    override fun onCleared() = productDetailRepository.onCleared()
}