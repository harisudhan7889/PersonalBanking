package au.com.nab.framework.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ErrorState
import au.com.nab.domain.common.LoadingState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.utility.ObjectMapper.mapNullInputList
import au.com.nab.framework.utility.ObjectMapper.mapRemoteProduct
import au.com.nab.framework.utility.ObjectMapper.mapRemoteProducts
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Class that implements the abstract source defined in the inner domain layer.
 *
 * @author Hari Hara Sudhan. N
 */
class ProductListDataSourceImpl @Inject constructor(val productListApi: ProductListApi,
                                                    val productDao: ProductsDao)
    : ProductListDataSource<ViewState<List<ProductsEntity>>> {

    private val productsListener = MutableLiveData<ViewState<List<ProductsEntity>>>()
    private val roomProductsLiveData by lazy {
        productDao.readAllProducts()
    }

    private val roomProductsLiveDataObserver = Observer<List<ProductsEntity>>{
        productsListener.value = DataState(it)
    }

    override fun fetchProducts() {
        //http://jsonblob.com/919227149077200896
        fetchFromDb()
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        productListApi
            .getBankingProducts()
            .flatMap {
                val products = mapRemoteProducts(it.data.products) { remoteProducts ->
                    mapNullInputList(remoteProducts) { remoteProduct ->
                        mapRemoteProduct(remoteProduct)
                    }
                }
                productDao.insertAllProducts(products)
                Single.just(products)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<ProductsEntity>> {
                override fun onSuccess(response: List<ProductsEntity>) {
                    // Successful response leads to a entry in the room db that triggers
                    // the live data listener
                }

                override fun onError(error: Throwable) {
                    productsListener.value =
                        ErrorState(error, null)
                }

                override fun onSubscribe(disposable: Disposable) {
                    productsListener.value =
                        LoadingState(null)
                }
            })
    }

    private fun fetchFromDb() {
        if (!roomProductsLiveData.hasActiveObservers()) {
            roomProductsLiveData.observeForever(roomProductsLiveDataObserver)
        }
    }

    override fun getObserver() =  productsListener

    /**
     * Unsubscribe and clear the resources.
     */
    override fun onCleared() {
        roomProductsLiveData.removeObserver(roomProductsLiveDataObserver)
    }
}