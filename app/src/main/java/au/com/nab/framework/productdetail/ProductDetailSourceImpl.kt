package au.com.nab.framework.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.DbProductEncapsuler
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.utility.ObjectMapper
import io.reactivex.Observable
import io.reactivex.Single
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
    ProductDetailDataSource<DbProductEncapsuler> {

    private val productListener = MutableLiveData<ViewState<DbProductEncapsuler>>()

    private var dbProductObserver: LiveData<DbProductEncapsuler>? = null

    private var disposable: Disposable? = null

    private val roomProductObserver = Observer<DbProductEncapsuler>{
        productListener.value = DataState(it)
    }

    override fun fetchProductById(id: String): Single<DbProductEncapsuler> {
        fetchProductFromDb(id)
        return fetchProductFromRemote(id)
    }

    private fun fetchProductFromDb(productId: String) {
        dbProductObserver = productDao.readProductById(productId)
        if (dbProductObserver?.hasActiveObservers() == false) {
            dbProductObserver?.observeForever(roomProductObserver)
        }
    }

    private fun fetchProductFromRemote(productId: String): Single<DbProductEncapsuler> {
        return productDetailApi.getProductById(productId).map {
            ObjectMapper.mapRemoteToLocalProduct(it.data)
        }
    }

    override fun getObserver() = productListener

    /**
     * Unsubscribe and clear the resources.
     */
    override fun onCleared() {
        dbProductObserver?.run {
            removeObserver(roomProductObserver)
        }
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    override fun cache(item: DbProductEncapsuler) {
        disposable = Observable.fromCallable {
            productDao.insertProductDetails(
                item.productsEntity,
                item.fees,
                item.features,
                item.eligibility,
                item.lendingRate
            )
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}