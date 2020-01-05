package cz.drekorian.avonfetcher.flow

import cz.drekorian.avonfetcher.CsvPrinter
import cz.drekorian.avonfetcher.I18n
import cz.drekorian.avonfetcher.getReadableCampaignId
import cz.drekorian.avonfetcher.http.brochure.folder.BrochureFolderRequest
import cz.drekorian.avonfetcher.http.brochure.summaries.BrochureSummariesRequest
import cz.drekorian.avonfetcher.http.productlistmodal.ProductListModalRequest
import cz.drekorian.avonfetcher.http.productlistmodal.ProductListModalResponse
import cz.drekorian.avonfetcher.model.brochure.Folder
import cz.drekorian.avonfetcher.model.brochure.Product
import mu.KotlinLogging

val logger = KotlinLogging.logger { }


class MasterFlow {

    companion object {

        private const val OUTPUT_FILE_NAME = "AvonFetcher%s.csv"
    }

    fun execute(campaignId: String) {
        val brochureSummariesResponse = BrochureSummariesRequest().send(campaignId)
        if (brochureSummariesResponse == null) {
            logger.error(I18n.get("master_flow_summaries_response_null"))
            return
        }

        val folders: List<Folder> = brochureSummariesResponse.folders

        // log fetched folders
        with(logger) {
            info(
                I18n.get("master_flow_brochure_summary").format(
                    getReadableCampaignId(campaignId),
                    folders.size
                )
            )

            folders.forEach {
                info(
                    I18n.get("master_flow_brochure_folder_debug_log").format(
                        it.title,
                        it.pageCount,
                        it.folder,
                        it.published
                    )
                )
            }
        }

        val products: MutableList<Product> = mutableListOf()
        for (folder: Folder in folders) {
            val brochureFolderResponse = BrochureFolderRequest().send(campaignId, folder.folder)
            if (brochureFolderResponse == null) {
                logger.error(I18n.get("master_flow_brochure_folder_response").format(folder.folder))
                return
            }

            brochureFolderResponse.run {
                products.addAll(brochureFolderResponse.products)
            }
        }

        val validProducts: List<String> = products
            .filter { it.productId != "0" }
            .map { it.productId }
            .distinct()

        val modalProducts: MutableMap<String?, List<cz.drekorian.avonfetcher.model.productlist.Product>> =
            mutableMapOf()

        validProducts.asSequence()
            .withIndex()
            .groupBy { (index, _) -> index / 10 }
            .forEach { (_, products) ->
                val productList = products.map { (_, values) -> values }
                val productListModalResponse: ProductListModalResponse? =
                    ProductListModalRequest().send(productList)
                if (productListModalResponse == null) {
                    logger.warn(I18n.get("master_flow_product_list_modal_response").format(productList))
                    // fallback (try each product ID separately)
                    for (product in products) {
                        val fallbackResponse: ProductListModalResponse? =
                            ProductListModalRequest().send(listOf(product.value))
                        if (fallbackResponse == null) {
                            logger.error(I18n.get("master_flow_product_fallback_failed").format(product))
                        } else {
                            modalProducts.putAll(fallbackResponse.products)
                        }
                    }
                } else {
                    modalProducts.putAll(productListModalResponse.products)
                }
            }

        val unifiedProducts: MutableList<cz.drekorian.avonfetcher.model.unified.Product> = mutableListOf()
        products.forEach { validProduct ->
            unifiedProducts.add(
                cz.drekorian.avonfetcher.model.unified.Product(
                    validProduct.productName,
                    validProduct.valid,
                    validProduct.productId,
                    validProduct.lineNumber,
                    validProduct.pageMin,
                    validProduct.pageMax,
                    modalProducts[validProduct.productId]?.single()?.isNew ?: false,
                    modalProducts[validProduct.productId]?.single()?.isOnSale ?: false,
                    modalProducts[validProduct.productId]?.single()?.listPrice ?: 0.0,
                    modalProducts[validProduct.productId]?.single()?.salePrice ?: 0.0,
                    modalProducts[validProduct.productId]?.single()?.unitPrice ?: 0.0,
                    modalProducts[validProduct.productId]?.single()?.description ?: " ",
                    modalProducts[validProduct.productId]?.single()?.ingredients ?: " "
                )
            )
        }

        val year = campaignId.substring(0..3)
        val campaignNumber = campaignId.substring(4..5)
        val fileName = OUTPUT_FILE_NAME.format(campaignId)

        CsvPrinter.print(
            fileName,
            unifiedProducts.map { it.toCsv(year, campaignNumber) },
            "Year",
            "Campaign",
            "pageMin",
            "pageMax",
            "productId",
            "lineNumber",
            "productName",
            "salePrice",
            "listPrice",
            "unitPrice",
            "valid",
            "isNew",
            "isOnSale",
            "description",
            "ingredients"
        )
    }
}
