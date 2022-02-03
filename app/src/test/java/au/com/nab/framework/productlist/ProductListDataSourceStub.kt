package au.com.nab.framework.productlist

import androidx.lifecycle.MutableLiveData
import au.com.nab.data.productlist.ProductListDataSource
import au.com.nab.domain.common.DataState
import au.com.nab.domain.common.ViewState
import au.com.nab.framework.ProductsEntity
import io.reactivex.Single

/**
 * @author Hari Hara Sudhan. N
 */
open class ProductListDataSourceStub: ProductListDataSource<List<ProductsEntity>> {

    private val productsListener = MutableLiveData<ViewState<List<ProductsEntity>>>()

    override fun fetchProducts(): Single<List<ProductsEntity>> {
            return Single.just(listOf(ProductsEntity("1", "", "", "", "")))
    }

    override fun getObserver(): MutableLiveData<ViewState<List<ProductsEntity>>> {
      return productsListener
    }

    override fun onCleared() {

    }

    override fun cache(item: List<ProductsEntity>) {
        productsListener.value = DataState(item)
    }
}