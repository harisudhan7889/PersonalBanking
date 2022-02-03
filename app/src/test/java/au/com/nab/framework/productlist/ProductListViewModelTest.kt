package au.com.nab.framework.productlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import au.com.nab.domain.common.*
import au.com.nab.domain.productlist.Data
import au.com.nab.domain.productlist.ProductListObject
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity
import au.com.nab.framework.SchedulerStub
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atMostOnce
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


/**
 * @author Hari Hara Sudhan. N
 */
class ProductListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    // For real data source class
    @Mock
    lateinit var productListApi: ProductListApi
    @Mock
    lateinit var productListDao: ProductsDao
    @Mock
    lateinit var observer: Observer<ViewState<List<ProductsEntity>>>
    private lateinit var productListDataSourceImpl: ProductListDataSourceImpl
    private lateinit var productListViewModel: ProductListViewModel


    // For the stubbed data source class
    @Mock
    lateinit var observerForStubbedDataSource: Observer<ViewState<List<ProductsEntity>>>
    private lateinit var productListViewModelForStubbedDs: ProductListViewModel

    @Before
    fun setUp() {
        productListDataSourceImpl = ProductListDataSourceImpl(productListApi, productListDao)
        productListViewModel = ProductListViewModel(productListDataSourceImpl, ProductListGroup(), SchedulerStub())
        productListViewModel.getProductsObserver().observeForever(observer)

        productListViewModelForStubbedDs = ProductListViewModel(ProductListDataSourceStub(), ProductListGroup(), SchedulerStub())
        productListViewModelForStubbedDs.getProductsObserver().observeForever(observerForStubbedDataSource)
    }

    @Test
    fun testNull() {
        Mockito.`when`(productListApi.getBankingProducts()).thenReturn(null)
        Assert.assertNotNull(productListViewModel.getProductsObserver())
        Assert.assertTrue(productListViewModel.getProductsObserver().hasObservers())
    }

    @Test
    fun testApiFetchLoading() {
        // Testing objects setup
        val product = Product(productId = "1")
        val data = Data(listOf(product))
        val productListObject = ProductListObject(data)

        // Expectation
        Mockito.`when`(productListApi.getBankingProducts()).thenReturn (
            Single.just(productListObject)
        )

        // Exercise
        productListViewModel.execute()

        // Validation
        Mockito.verify(observer, atMostOnce()).onChanged(LoadingState(null))
    }

    @Test
    fun testApiFetchError() {
        val outOfMemoryError = OutOfMemoryError()

        // Expectation
        Mockito.`when`(productListApi.getBankingProducts()).thenReturn (
            Single.error(outOfMemoryError)
        )

        // Exercise
        productListViewModel.execute()

        // Validation
        Mockito.verify(observer, atMostOnce()).onChanged(LoadingState(null))
        Mockito.verify(observer, atMostOnce()).onChanged(ErrorState(outOfMemoryError, null))
    }

    @Test
    fun testApiFetchSuccess() {
        val productsEntity = ProductsEntity("1", "", "", "", "")

        // Exercise
        productListViewModelForStubbedDs.execute()

        // Validation
        Mockito.verify(observerForStubbedDataSource, atMostOnce()).onChanged(LoadingState(null))
        Mockito.verify(observerForStubbedDataSource, atMostOnce()).onChanged(DataState(listOf(productsEntity)))
    }
}