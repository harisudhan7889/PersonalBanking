package au.com.nab.presentation.productlist

/**
 * @author Hari Hara Sudhan. N
 */
data class ProductCategoryObject(val productCategoryName: String, val productCategoryThumbnail: String)

class ProductCategory {
    companion object {

        const val BASE_URL = "https://www.nab.com.au/content/dam/"

        fun getProductCategoryObject(productCatgory: String): ProductCategoryObject {
            var categoryLabel: String? = null
            var categoryThumbnail: String? = null
            when(productCatgory) {
                "BUSINESS_LOANS" -> {
                    categoryLabel = "Business Loans"
                    categoryThumbnail = "${BASE_URL}nab/images/types/banners/smiling-girl-banner-2500x900.jpg"
                }
                "PERS_LOANS" -> {
                    categoryLabel = "Personal Loans"
                    categoryThumbnail = "${BASE_URL}nabrwd/personal/loans/images/grey-white-banner-2500x900.jpg"
                }
                "CRED_AND_CHRG_CARDS" -> {
                    categoryLabel = "Credit Cards"
                    categoryThumbnail = "${BASE_URL}nabrwd/common/target/masthead/personal/credit-card-Category_Rewards-2500x900.jpg"
                }
                "TRANS_AND_SAVINGS_ACCOUNTS" -> {
                    categoryLabel = "Transaction and savings"
                    categoryThumbnail = "${BASE_URL}nabrwd/personal/images/responsive/nab-retirement-account.jpg.image.768.medium.2x1.jpg/1583903544702.jpg"
                }
                "OVERDRAFTS" -> {
                    categoryLabel = "Over Drafts"
                    categoryThumbnail = "${BASE_URL}nabrwd/business/images/responsive/nab-farm-management-account-overdraft.jpg"
                }
                "TERM_DEPOSITS" -> {
                    categoryLabel = "Term Deposits"
                    categoryThumbnail = "${BASE_URL}nabrwd/personal/banking/term-deposits/images/term-deposit-mature-couple-coffee.jpg.image.1024.medium.2_15x1.jpg/1584076560541.jpg"
                }
                "LEASES" -> {
                    categoryLabel = "Leases"
                    categoryThumbnail = "${BASE_URL}nabrwd/business/loans-and-finance/images/equipment-finance-van-3.jpg.image.768.medium.2x1.jpg/1576645395868.jpg"
                }
                "RESIDENTIAL_MORTGAGES" -> {
                    categoryLabel = "Residential Mortgages"
                    categoryThumbnail = "${BASE_URL}nab/images/types/banners/father-picking-up-daughter-banner-2500x900.jpg"
                }
                "REGULATED_TRUST_ACCOUNTS" -> {
                    categoryLabel = "Regulated trust accounts"
                    categoryThumbnail = "${BASE_URL}nabrwd/business/images/responsive/business-statutory-trust-accounts.jpg.image.1024.medium.3x1.jpg/1552625140420.jpg"
                }
                "MARGIN_LOANS" -> {
                    categoryLabel = "Margin Loans"
                    categoryThumbnail = "${BASE_URL}nabrwd/business/images/overrides/override-online-banking.jpg.image.full.medium.3x1.jpg/1552452446897.jpg"
                }
                else -> {
                    categoryLabel = "Others"
                    categoryThumbnail =
                        "${BASE_URL}content/dam/nab/images/types/banners/woman-traveller-hatchback-car-mountain-2500x900.jpg"
                }
            }
            return ProductCategoryObject(categoryLabel, categoryThumbnail)
        }
    }
}