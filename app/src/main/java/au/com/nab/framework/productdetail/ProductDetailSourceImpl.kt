package au.com.nab.framework.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import au.com.nab.data.productdetail.ProductDetailDataSource
import au.com.nab.domain.DataState
import au.com.nab.domain.ErrorState
import au.com.nab.domain.LoadingState
import au.com.nab.domain.ViewState
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.mapper.ObjectMapper
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
class ProductDetailSourceImpl @Inject constructor(val productDetailApi: ProductDetailApi,
                                                  val productDao: ProductsDao):
    ProductDetailDataSource<ViewState<ProductsEntity>> {

    private val productDetailListener = MutableLiveData<ViewState<ProductsEntity>>()

    private var dbProductObserver: LiveData<ProductsEntity>? = null

    private val roomProductObserver = Observer<ProductsEntity>{
        productDetailListener.value = DataState(it)
    }

    override fun fetchProductById(productId: String) {
        fetchProductFromDb(productId)
        fetchProductFromRemote(productId)
    }

    private fun fetchProductFromDb(productId: String) {
        dbProductObserver = productDao.readById(productId)
        dbProductObserver?.observeForever(roomProductObserver)
    }

    private fun fetchProductFromRemote(productId: String) {
        productDetailApi.getProductById(productId).flatMap {
            val product = ObjectMapper.mapRemoteProduct(it.data)
            productDao.update(product)
            Single.just(product)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductsEntity> {
                override fun onSuccess(product: ProductsEntity) {
                    productDetailListener.value = DataState(product)
                    // Successful response leads to a entry in the room db that triggers
                    // the live data listener
                }

                override fun onSubscribe(disposable: Disposable) {
                    productDetailListener.value = LoadingState(null)
                }

                override fun onError(error: Throwable) {
                    productDetailListener.value = ErrorState(error, null)
                }

            })
    }

    override fun getObserver() = productDetailListener

    override fun onCleared() {
        dbProductObserver?.run{
            removeObserver(roomProductObserver)
        }
    }
}