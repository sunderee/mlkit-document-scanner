package com.peteralexbizjak.mlkit_document_scanner

// Miscellaneous
internal const val LOGGING_TAG = "MLKit Document Scanner"

// Channel names
internal const val METHOD_CHANNEL = "mlkit_document_scanner_method_channel"
internal const val EVENT_CHANNEL_JPEG = "mlkit_document_scanner_event_channel_jpeg"
internal const val EVENT_CHANNEL_PDF = "mlkit_document_scanner_event_channel_pdf"

// Method names
internal const val START_DOCUMENT_SCANNER = "startDocumentScanner"

// Argument names
internal const val ARGUMENT_NUMBER_OF_PAGES = "maximumNumberOfPages"
internal const val ARGUMENT_GALLERY_IMPORT_ALLOWED = "galleryImportAllowed"
internal const val ARGUMENT_SCANNER_MODE = "scannerMode"
internal const val ARGUMENT_RESULT_MODE = "resultMode"

// Error codes and messages
internal const val ERROR_CODE_START_SCAN_INTENT_FAILURE = "error-start-scan-intent"
internal const val ERROR_MESSAGE_START_SCAN_INTENT_FAILURE =
    "Something went wrong trying to launch the Document Scanner activity"
