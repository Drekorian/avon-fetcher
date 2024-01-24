package cz.drekorian.avonfetcher.flow

import cz.drekorian.avonfetcher.CsvPrinter
import cz.drekorian.avonfetcher.I18n
import cz.drekorian.avonfetcher.getReadableCampaignId
import cz.drekorian.avonfetcher.http.brochure.folder.BrochureFolderRequest
import cz.drekorian.avonfetcher.http.brochure.summaries.BrochureSummariesRequest
import cz.drekorian.avonfetcher.http.productlistmodal.ProductListModalRequest
import cz.drekorian.avonfetcher.http.productlistmodal.ProductListModalResponse
import cz.drekorian.avonfetcher.model.brochure.Folder
import cz.drekorian.avonfetcher.model.brochure.Page
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import cz.drekorian.avonfetcher.model.unified.Product as UnifiedProduct

val logger = KotlinLogging.logger { }

class MasterFlow {

    companion object {

        private const val OUTPUT_FILE_NAME = "AvonFetcher%s.csv"
    }

    fun execute(campaignId: String) {
        val brochureSummariesResponse = runBlocking { BrochureSummariesRequest().send(campaignId) }
        if (brochureSummariesResponse == null) {
            logger.error(I18n.get("master_flow_summaries_response_null"))
            return
        }

        val folders: List<Folder> = brochureSummariesResponse.data

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
                        it.isPublished
                    )
                )
            }
        }

        val pages: List<Page> = buildList {
            for (folder: Folder in folders) {
                val brochureFolderResponse = runBlocking { BrochureFolderRequest().send(campaignId, folder.folder) }
                if (brochureFolderResponse == null) {
                    logger.error(I18n.get("master_flow_brochure_folder_response").format(folder.folder))
                    return
                }

                addAll(brochureFolderResponse.pages)
            }
        }

        val validProducts: List<String> = pages.asSequence()
            .flatMap { page -> page.hotSpots ?: emptyList() }
            .flatMap { hotspot -> hotspot.products ?: emptyList() }
            .filter { it.productId != "0" }
            .map { product -> product.productId }
            .distinct()
            .toList()

        val modalProducts: MutableMap<String?, List<cz.drekorian.avonfetcher.model.productlist.Product>> =
            mutableMapOf()

        validProducts.asSequence()
            .withIndex()
            .groupBy { (index, _) -> index / 10 }
            .forEach { (_, products) ->
                val productList = products.map { (_, values) -> values }
                val productListModalResponse: ProductListModalResponse? = runBlocking {
                    ProductListModalRequest().send(productList)
                }
                if (productListModalResponse == null) {
                    logger.warn(I18n.get("master_flow_product_list_modal_response").format(productList))
                    // fallback (try each product ID separately)
                    for (product in products) {
                        val fallbackResponse: ProductListModalResponse? =
                            runBlocking { ProductListModalRequest().send(listOf(product.value)) }
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

        val unifiedProducts: MutableList<UnifiedProduct> = mutableListOf()
        pages.forEach { page ->
            page.hotSpots?.flatMap { hotSpot -> hotSpot.products ?: emptyList() }?.forEach { validProduct ->
                unifiedProducts.add(
                    UnifiedProduct(
                        productName = validProduct.productName,
                        valid = validProduct.isValid,
                        productId = validProduct.productId,
                        lineNumber = validProduct.lineNumber,
                        pageMin = page.pageMin,
                        pageMax = page.pageMax,
                        isNew = modalProducts[validProduct.productId]?.single()?.isNew ?: false,
                        isOnSale = modalProducts[validProduct.productId]?.single()?.isOnSale ?: false,
                        listPrice = modalProducts[validProduct.productId]?.single()?.listPrice ?: 0.0,
                        salePrice = modalProducts[validProduct.productId]?.single()?.salePrice ?: 0.0,
                        unitPrice = modalProducts[validProduct.productId]?.single()?.unitPrice ?: 0.0,
                        description = modalProducts[validProduct.productId]?.single()?.description ?: " ",
                        ingredients = modalProducts[validProduct.productId]?.single()?.ingredients ?: " "
                    )
                )
            }
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
