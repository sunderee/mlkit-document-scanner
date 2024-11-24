package com.peteralexbizjak.mlkit_document_scanner

import android.content.Intent
import android.util.Log
import androidx.core.net.toFile
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import io.flutter.plugin.common.EventChannel

internal class MlkitDocumentScannerLibrary {
    fun buildGmsDocumentScanner(
        maximumNumberOfPages: Int,
        galleryImportAllowed: Boolean,
        scannerMode: Int,
        resultMode: DocumentScannerResultMode
    ): GmsDocumentScanner {
        Log.d(
            LOGGING_TAG, "Building document scanner for:\n" +
                    "    - maximum number of pages: $maximumNumberOfPages\n" +
                    "    - gallery imports allowed: $galleryImportAllowed\n" +
                    "    - scanner mode: $scannerMode\n" +
                    "    - result mode: ${resultMode.ordinal}\n"
        )
        var options = GmsDocumentScannerOptions.Builder()
            .setGalleryImportAllowed(galleryImportAllowed)
            .setPageLimit(maximumNumberOfPages)

        options = when (resultMode) {
            DocumentScannerResultMode.JPEG_PAGES -> options.setResultFormats(
                GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
            )

            DocumentScannerResultMode.PDF_FILE -> options.setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)

            DocumentScannerResultMode.BOTH -> options.setResultFormats(
                GmsDocumentScannerOptions.RESULT_FORMAT_JPEG,
                GmsDocumentScannerOptions.RESULT_FORMAT_PDF
            )
        }

        options = options.setScannerMode(scannerMode)

        return GmsDocumentScanning.getClient(options.build())
    }

    fun handleActivityResult(
        data: Intent?,
        eventSinkJPEG: EventChannel.EventSink?,
        eventSinkPDF: EventChannel.EventSink?
    ) {
        val documentScanningResult = GmsDocumentScanningResult.fromActivityResultIntent(data)
        if (documentScanningResult == null) {
            Log.d(LOGGING_TAG, "NOTHING CAME BACK FROM THE SCAN!!!")
            return
        }

        documentScanningResult.also { result ->
            result.pages.let {
                if (it.isNullOrEmpty()) {
                    Log.d(LOGGING_TAG, "null response (result.pages)")
                    eventSinkJPEG?.success(null)
                } else {
                    Log.d(LOGGING_TAG, "JPEG pages count ${it.size}")
                    eventSinkJPEG?.success(it.map { page ->
                        page.imageUri.toFile().readBytes()
                    })
                }


            }
            result.pdf.let {
                if (it != null && it.pageCount > 0) {
                    Log.d(LOGGING_TAG, "PDF pages count ${it.pageCount}")
                    eventSinkPDF?.success(it.uri.toFile().readBytes())
                } else {
                    Log.d(LOGGING_TAG, "null response (result.pdf)")
                    eventSinkPDF?.success(null)
                }
            }
        }
    }
}