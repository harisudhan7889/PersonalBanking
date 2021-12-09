package au.com.nab.domain.productlist

import au.com.nab.domain.Product

/**
 *@author Hari Hara Sudhan. N
 */
data class ProductObject(val data: Data)

data class Data(val products: List<Product>?)
