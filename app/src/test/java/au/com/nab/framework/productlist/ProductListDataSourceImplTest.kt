package au.com.nab.framework.productlist

import au.com.nab.domain.common.Product
import au.com.nab.domain.productlist.Data
import au.com.nab.domain.productlist.ProductListObject
import au.com.nab.framework.ProductsDao
import au.com.nab.framework.ProductsEntity
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


/**
 * @author Hari Hara Sudhan. N
 */
class ProductListDataSourceImplTest {

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var productListApi: ProductListApi

    @Mock
    lateinit var productListDao: ProductsDao

    lateinit var productListDataSourceImpl: ProductListDataSourceImpl
    lateinit var productListRepository: ProductListRepository

    @Before
    fun setUp() {
        productListDataSourceImpl = ProductListDataSourceImpl(productListApi, productListDao)
        productListRepository = ProductListRepository(productListDataSourceImpl)
        //RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        //RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testFetchingProductsUsingBlockingCall() {
        // Testing objects setup
        val product = Product(productId = "1")
        val data = Data(listOf(product))
        val productListObject = ProductListObject(data)

        //Expectation
        `when`(productListApi.getBankingProducts()).thenReturn(
            Single.just(productListObject)
        )
        val products = productListDataSourceImpl.fetchProducts().blockingGet()

        //Verification
        assertEquals(products.size, 1)
        assertEquals(product.productId, products.first().productId)
    }

    @Test
    fun testFetchingProductsWithoutError() {
        // Testing objects setup
        val product = Product(productId = "1")
        val data = Data(listOf(product))
        val productListObject = ProductListObject(data)

        //Expectation
        `when`(productListApi.getBankingProducts()).thenReturn(
            Single.just(productListObject)
        )

        val testObserver = productListDataSourceImpl.fetchProducts().test()
        testObserver.awaitTerminalEvent()

        // Verification
        testObserver
            .assertNoErrors()
            .assertValue { products -> products.size == 1 }

    }

    @Test
    fun testFetchingProductsWithUnhandledError() {
        //Expectation
        `when`(productListApi.getBankingProducts()).thenReturn(
            Single.error(OutOfMemoryError(""))
        )
        val testObserver = productListDataSourceImpl.fetchProducts().test()
        testObserver.awaitTerminalEvent()
        // Verification
        testObserver.assertError(OutOfMemoryError::class.java)
        testObserver.assertNotComplete()
        testObserver.assertNoValues()
    }

    @Test
    fun testObjectMapping() {
        // Testing objects setup
        val product1 = Product(productId = "1")
        val product2 = Product(productId = "2")
        val data = Data(listOf(product1, product2))
        val productObject = ProductListObject(data)

        //Expectation
        `when`(productListApi.getBankingProducts()).thenReturn(
            Single.just(productObject)
        )

        val testObserver = productListDataSourceImpl.fetchProducts().test()
        testObserver.awaitTerminalEvent()

        // Verification
        testObserver
            .assertNoErrors()
            .assertValue { products ->
                Observable.fromIterable(products)
                    .map (ProductsEntity::productId)
                    .toList()
                    .blockingGet() == listOf("1", "2")
            }
    }
}