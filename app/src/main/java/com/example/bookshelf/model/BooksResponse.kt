package com.example.bookshelf.model


data class BooksResponse(
//    val kind: String?,
//    val totalItems: Int?,
    val items: List<BookItem>?
)

data class BookItem(
//    val kind: String?,
    val id: String?,
//    val etag: String?,
//    val selfLink: String?,
    val volumeInfo: VolumeInfo?,
//    val saleInfo: SaleInfo?,
//    val accessInfo: AccessInfo?,
//    val searchInfo: SearchInfo?
)

data class VolumeInfo(
//    val title: String?,
//    val subtitle: String?,
//    val authors: List<String>?,
//    val publisher: String?,
//    val publishedDate: String?,
//    val description: String?,
//    val industryIdentifiers: List<IndustryIdentifier>?,
//    val readingModes: ReadingModes?,
//    val pageCount: Int?,
//    val printType: String?,
//    val categories: List<String>?,
//    val maturityRating: String?,
//    val allowAnonLogging: Boolean?,
//    val contentVersion: String?,
//    val panelizationSummary: PanelizationSummary?,
    val imageLinks: ImageLinks?,
//    val language: String?,
//    val previewLink: String?,
//    val infoLink: String?,
//    val canonicalVolumeLink: String?
)

//@Serializable
//data class IndustryIdentifier(
//    val type: String?,
//    val identifier: String?
//)

//data class ReadingModes(
//    val text: Boolean?,
//    val image: Boolean?
//)

//@Serializable
//data class PanelizationSummary(
//    val containsEpubBubbles: Boolean?,
//    val containsImageBubbles: Boolean?
//)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?,
    val small: String?,
    val medium: String?,
    val large: String?,
    val extraLarge: String?
)

//data class SaleInfo(
//    val country: String?,
//    val saleability: String?,
//    val isEbook: Boolean?,
//    val listPrice: Price?,
//    val retailPrice: Price?,
//    val buyLink: String?,
//    val offers: List<Offer>?
//)
//
//data class Price(
//    val amount: Double?,
//    val currencyCode: String?,
//    val amountInMicros: Long? = null // appears only inside offers
//)
//
//data class Offer(
//    val finskyOfferType: Int?,
//    val listPrice: Price?,
//    val retailPrice: Price?
//)

//@Serializable
//data class AccessInfo(
//    val country: String?,
//    val viewability: String?,
//    val embeddable: Boolean?,
//    val publicDomain: Boolean?,
//    val textToSpeechPermission: String?,
//    val epub: Format?,
//    val pdf: Format?,
//    val webReaderLink: String?,
//    val accessViewStatus: String?,
//    val quoteSharingAllowed: Boolean?
//)

//@Serializable
//data class Format(
//    val isAvailable: Boolean?,
//    val acsTokenLink: String? = null
//)
//
//@Serializable
//data class SearchInfo(
//    val textSnippet: String?
//)