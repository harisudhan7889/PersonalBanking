package au.com.nab.framework.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ErrorState
import au.com.nab.domain.common.LoadingState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductData
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.utility.ObjectMapper
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Class that implements the abstract source defined in the inner domain layer.
 *
 * @author Hari Hara Sudhan. N
 */
class ProductDetailSourceImpl @Inject constructor(val productDetailApi: ProductDetailApi,
                                                  val productDao: ProductsDao):
    ProductDetailDataSource<ViewState<ProductData>> {

    private val productListener = MutableLiveData<ViewState<ProductData>>()

    private var dbProductObserver: LiveData<ProductData>? = null

    private val roomProductObserver = Observer<ProductData>{
        productListener.value = DataState(it)
    }

    override fun fetchProductById(productId: String) {
        fetchProductFromDb(productId)
        fetchProductFromRemote(productId)
    }

    private fun fetchProductFromDb(productId: String) {
        dbProductObserver = productDao.readProductById(productId)
        dbProductObserver?.observeForever(roomProductObserver)
    }

    private fun fetchProductFromRemote(productId: String) {
        productDetailApi.getProductById(productId).flatMapCompletable {
            val product = ObjectMapper.mapRemoteProduct(it.data)

            val productFeature =
                ObjectMapper.mapRemoteProductFeatures(it.data.features) { remoteFeatures ->
                    ObjectMapper.mapNullInputList(remoteFeatures) { remoteFeature ->
                        ObjectMapper.mapRemoteFeature(productId, remoteFeature)
                    }
                }

            val productFee = ObjectMapper.mapRemoteProductFees(it.data.fees) { remoteFees ->
                ObjectMapper.mapNullInputList(remoteFees) { remoteFee ->
                    ObjectMapper.mapRemoteFee(productId, remoteFee)
                }
            }

            val productEligibility =
                ObjectMapper.mapRemoteProductEligibility(it.data.eligibility) { remoteEligibilities ->
                    ObjectMapper.mapNullInputList(remoteEligibilities) { remoteEligibility ->
                        ObjectMapper.mapRemoteEligibility(productId, remoteEligibility)
                    }
                }

            val productLendingRates =
                ObjectMapper.mapRemoteProductLendingRate(it.data.lendingRates) { remoteLendingRates ->
                    ObjectMapper.mapNullInputList(remoteLendingRates) { remoteLendingRate ->
                        ObjectMapper.mapRemoteLendingRate(productId, remoteLendingRate)
                    }
                }

            productDao.insertProductDetails(
                product,
                productFee,
                productFeature,
                productEligibility,
                productLendingRates
            )
            Completable.complete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {

                }

                override fun onSubscribe(disposable: Disposable) {
                    productListener.value =
                        LoadingState(null)
                }

                override fun onError(error: Throwable) {
                    productListener.value =
                        ErrorState(error, null)
                }

            })
    }

    override fun getObserver() = productListener

    /**
     * Unsubscribe and clear the resources.
     */
    override fun onCleared() {
        dbProductObserver?.run{
            removeObserver(roomProductObserver)
        }
    }
}