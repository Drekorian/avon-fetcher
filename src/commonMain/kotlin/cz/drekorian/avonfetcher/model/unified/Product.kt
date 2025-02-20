package cz.drekorian.avonfetcher.model.unified

import cz.drekorian.avonfetcher.CSV_SEPARATOR

data class Product(
    val productName: String,
    val valid: Boolean,
    val productId: String,
    val lineNumber: String,
    val pageMin: Int?,
    val pageMax: Int?,
    val isNew: Boolean,
    val isOnSale: Boolean,
    val listPrice: Double,
    val salePrice: Double,
    val unitPrice: Double,
    val description: String,
    val ingredients: String
) {

    companion object {

        private const val PRODUCT_ID_LENGTH = 5
        private const val PRODUCT_ID_PADDING = '0'
        private const val TRUE = -1
        private const val FALSE = 0
        private const val LINE_SEPARATOR = "____"
    }

    /**
     * Serializes the product into the CSV data format.
     *
     * @param year year of the campaign (e.g. "2019")
     * @param campaignNumber number of the campaign within the year (e.g. "14")
     */
    fun toCsv(year: String, campaignNumber: String): String = """
        |$year
        |$campaignNumber
        |$pageMin
        |$pageMax
        |="${productId.padStart(PRODUCT_ID_LENGTH, PRODUCT_ID_PADDING)}"
        |="${lineNumber.padStart(PRODUCT_ID_LENGTH, PRODUCT_ID_PADDING)}"
        |$productName
        |$salePrice
        |$listPrice
        |$unitPrice
        |${if (valid) TRUE else FALSE}
        |${if (isNew) TRUE else FALSE}
        |${if (isOnSale) TRUE else FALSE}
        |"${description
            .lines()
            .joinToString(separator = LINE_SEPARATOR)
        }"
        $ingredients"""
        .lines()
        .filterIndexed { index, _ -> index != 0 }
        .joinToString(separator = CSV_SEPARATOR) { it.trimMargin("|") }
        .replace(LINE_SEPARATOR, "\n")
}
