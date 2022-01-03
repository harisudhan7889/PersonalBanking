package au.com.nab.framework.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ErrorState
import au.com.nab.domain.common.LoadingState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsEntity
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
class ProductListViewModel @Inject constructor(val productListRepository: ProductListRepository,
                                               val productListGroup: ProductListGroup): ViewModel() {

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
        productListRepository.fetchProducts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
                    productListRepository.cacheProducts(products)
                }
            })
    }

    /**
     * Gets the products without any grouping. Raw product list.
     */
    fun getProductsObserver(): MutableLiveData<ViewState<List<ProductsEntity>>> = productListRepository.getObserver()

    /**
     * Get the products grouped by category.
     */
    fun getGroupedProductsObserver(): MutableLiveData<ViewState<Map<String, List<ProductsEntity>>>> {
        getProductsObserver().observeForever(productsLiveDataObserver)
        return productsGroupedLiveData
    }

    override fun onCleared() {
        productListRepository.onCleared()
    }
}