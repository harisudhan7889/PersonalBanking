package au.com.nab.framework.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ErrorState
import au.com.nab.domain.common.LoadingState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.scheduler.BaseScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author Hari Hara Sudhan. N
 */
@HiltViewModel
class ProductListViewModel @Inject constructor(val productListDataSource: ProductListDataSource<List<ProductsEntity>>,
                                               val productListGroup: ProductListGroup,
                                               val scheduler: BaseScheduler
): ViewModel() {

    private val productsGroupedLiveData = MutableLiveData<ViewState<Map<String, List<ProductsEntity>>>>()

    private val productsLiveDataObserver = Observer<ViewState<List<ProductsEntity>>>{
       when(it) {
           is DataState -> {
               productsGroupedLiveData.value = DataState(productListGroup.groupProductsByCategory(it.data))
           }
           is LoadingState -> {
               productsGroupedLiveData.value = LoadingState(null)
           }
           is ErrorState -> {
               productsGroupedLiveData.value = ErrorState(it.error, null)
           }
       }
    }

    fun execute() {
        productListDataSource.fetchProducts().subscribeOn(scheduler.getIoScheduler())
            .observeOn(scheduler.getMainThread())
            .subscribe(object : SingleObserver<List<ProductsEntity>> {
                override fun onError(error: Throwable) {
                    getProductsObserver().value =
                        ErrorState(error, null)
                }
                override fun onSubscribe(disposable: Disposable) {
                    getProductsObserver().value =
                        LoadingState(null)
                }
                override fun onSuccess(products: List<ProductsEntity>) {
                    productListDataSource.cache(products)
                }
            })
    }

    /**
     * Gets the products without any grouping. Raw product list.
     */
    fun getProductsObserver(): MutableLiveData<ViewState<List<ProductsEntity>>> = productListDataSource.getObserver()

    /**
     * Get the products grouped by category.
     */
    fun getGroupedProductsObserver(): MutableLiveData<ViewState<Map<String, List<ProductsEntity>>>> {
        getProductsObserver().observeForever(productsLiveDataObserver)
        return productsGroupedLiveData
    }

    override fun onCleared() {
        productListDataSource.onCleared()
    }
}