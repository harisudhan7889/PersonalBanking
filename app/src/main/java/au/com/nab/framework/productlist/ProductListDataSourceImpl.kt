package au.com.nab.framework.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.domain.DataState
import au.com.nab.domain.ErrorState
import au.com.nab.domain.LoadingState
import au.com.nab.domain.ViewState
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.mapper.ObjectMapper.mapNullInputList
import au.com.nab.framework.mapper.ObjectMapper.mapRemoteProduct
import au.com.nab.framework.mapper.ObjectMapper.mapRemoteProducts
import io.reactivex.Single
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
class ProductListDataSourceImpl @Inject constructor(val productListApi: ProductListApi,
                                                    val productDao: ProductsDao)
    : ProductListDataSource<ViewState<List<ProductsEntity>>> {

    private val productsListener = MutableLiveData<ViewState<List<ProductsEntity>>>()
    private val roomProductsLiveData by lazy {
        productDao.readAll()
    }
    private val roomProductsObserver = Observer<List<ProductsEntity>>{
        productsListener.value = DataState(it)
    }

    override fun fetchProducts() {
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
                productDao.insertAll(products)
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
                    productsListener.value = LoadingState(null)
                }
            })
    }

    private fun fetchFromDb() {
        if (!roomProductsLiveData.hasActiveObservers()) {
            roomProductsLiveData.observeForever(roomProductsObserver)
        }
    }

    override fun getObserver() =  productsListener

    override fun onCleared() {
        roomProductsLiveData.removeObserver(roomProductsObserver)
    }
}