package com.peteralexbizjak.mlkit_document_scanner

internal data class MLKitDocumentScannerConfiguration(
    val maximumNumberOfPages: Int,
    val galleryImportAllowed: Boolean,
    val scannerMode: Int
)
