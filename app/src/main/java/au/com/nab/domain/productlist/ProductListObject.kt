package au.com.nab.domain.productlist

import au.com.nab.domain.common.Product

/**
 *@author Hari Hara Sudhan. N
 */
data class ProductListObject(val data: Data)

data class Data(val products: List<Product>?)
