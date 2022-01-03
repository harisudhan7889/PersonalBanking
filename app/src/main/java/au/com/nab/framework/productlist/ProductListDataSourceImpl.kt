package au.com.nab.framework.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.utility.ObjectMapper.mapNullInputList
import au.com.nab.framework.utility.ObjectMapper.mapRemoteProduct
import au.com.nab.framework.utility.ObjectMapper.mapRemoteProducts
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
open class ProductListDataSourceImpl @Inject constructor(val productListApi: ProductListApi,
                                                    val productDao: ProductsDao)
    : ProductListDataSource<List<ProductsEntity>> {

    private val productsListener = MutableLiveData<ViewState<List<ProductsEntity>>>()
    private var roomProductsLiveData: LiveData<List<ProductsEntity>>? = productDao.readAllProducts()
    private var disposable: Disposable? = null

    private val roomProductsLiveDataObserver = Observer<List<ProductsEntity>> {
        productsListener.value = DataState(it)
    }

    init {
        setDatabaseEntryListener()
    }

    override fun fetchProducts(): Single<List<ProductsEntity>> {
        return productListApi
            .getBankingProducts()
            .map {
                mapRemoteProducts(it.data.products) { remoteProducts ->
                    mapNullInputList(remoteProducts) { remoteProduct ->
                        mapRemoteProduct(remoteProduct)
                    }
                }
            }
    }

    private fun setDatabaseEntryListener() {
        if (roomProductsLiveData?.hasActiveObservers() == false) {
            roomProductsLiveData?.observeForever(roomProductsLiveDataObserver)
        }
    }

    override fun getObserver() =  productsListener

    /**
     * Unsubscribe and clear the resources.
     */
    override fun onCleared() {
        roomProductsLiveData?.removeObserver(roomProductsLiveDataObserver)
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    override fun cache(products: List<ProductsEntity>) {
        disposable = Observable.fromCallable {
            productDao.insertAllProducts(products)
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}