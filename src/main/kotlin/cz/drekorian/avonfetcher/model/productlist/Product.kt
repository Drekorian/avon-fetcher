package cz.drekorian.avonfetcher.model.productlist

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Product(
    @JsonNames("Id") val id: String,
    @JsonNames("Name") val name: String,
    @JsonNames("ListPrice") val listPrice: Double? = null,
    @JsonNames("SalePrice") val salePrice: Double? = null,
    @JsonNames("UnitPrice") val unitPrice: Double,
    @JsonNames("Description") private val _description: String? = null,
    @JsonNames("IsNew") val isNew: Boolean,
    @JsonNames("IsOnSale") val isOnSale: Boolean,
    @JsonNames("Ingredients") val ingredients: String? = null,
) {

    val description: String?
        get() = _description?.replace(oldValue = ";", newValue = "")
}
