package cz.drekorian.avonfetcher.http.productlistmodal

import cz.drekorian.avonfetcher.model.productlist.Product
import cz.drekorian.avonfetcher.model.productlist.ProductModal
import kotlinx.serialization.Serializable

@Serializable
class ProductListModalResponse(
    private val productModals: List<ProductModal>,
) {

    val products: Map<String?, List<Product>>
        get() = productModals
            .map { modal -> modal.product }
            .groupBy { it.id }
}
