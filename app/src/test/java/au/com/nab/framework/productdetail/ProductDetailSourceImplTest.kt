package au.com.nab.framework.productdetail

import au.com.nab.domain.common.Product
import au.com.nab.domain.productdetail.ProductDetail
import au.com.nab.framework.ProductsDao
import io.reactivex.Single
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * @author Hari Hara Sudhan. N
 */
class ProductDetailSourceImplTest {

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var productDetailApi: ProductDetailApi

    @Mock
    lateinit var productsDao: ProductsDao

    var productDetailDataSourceImpl: ProductDetailSourceImpl? = null
    var productListRepository: ProductDetailRepository? = null

    @Before
    fun setUp() {
        productDetailDataSourceImpl = ProductDetailSourceImpl(productDetailApi, productsDao)
        productListRepository = ProductDetailRepository(productDetailDataSourceImpl!!)
    }

    @Test
    fun fetchProductByIdWithoutError() {
        // Object setup
        val productDetail = ProductDetail(Product("1"))

        // Expectation
        Mockito.`when`(productDetailApi.getProductById("1")).thenReturn(
            Single.just(productDetail)
        )

        val testObserver = productDetailDataSourceImpl!!.fetchProductById("1").test()
        testObserver.awaitTerminalEvent()

        // Verification
        testObserver
            .assertNoErrors()
            .assertValue {
                it.productsEntity.productId == "1"
            }
    }

    @Test
    fun fetchProductByIdWithUnHandledException() {
        // Expectation
        Mockito.`when`(productDetailApi.getProductById("1")).thenReturn(
            Single.error(StackOverflowError(""))
        )

        val testObserver = productDetailDataSourceImpl!!.fetchProductById("1").test()
        testObserver.awaitTerminalEvent()

        // Verification
        testObserver.assertError(StackOverflowError::class.java)
        testObserver.assertNotComplete()
        testObserver.assertNoValues()
    }

    @After
    fun tearDown() {
        productDetailDataSourceImpl = null
        productListRepository = null
    }
}