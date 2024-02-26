package com.peteralexbizjak.mlkit_document_scanner

import android.app.Activity.RESULT_OK
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import java.lang.Exception

typealias OnPageDocumentScannedCallback = (pages: List<GmsDocumentScanningResult.Page>) -> Unit
typealias OnPDFDocumentScannedCallback = (pdf: GmsDocumentScanningResult.Pdf) -> Unit
typealias OnTaskFailedCallback = (exception: Exception) -> Unit

internal class MLKitDocumentScanner(
    private val activity: AppCompatActivity,
    maximumNumberOfPages: Int,
    galleryImportAllowed: Boolean,
    scannerMode: Int
) {
    private var documentScannerOptions: GmsDocumentScannerOptions =
        GmsDocumentScannerOptions.Builder()
            .setGalleryImportAllowed(galleryImportAllowed)
            .setPageLimit(maximumNumberOfPages)
            .setResultFormats(
                GmsDocumentScannerOptions.RESULT_FORMAT_JPEG,
                GmsDocumentScannerOptions.RESULT_FORMAT_PDF
            )
            .setScannerMode(scannerMode)
            .build()

    fun beginListeningForDocumentScannerOutputs(
        onPageDocumentScanned: OnPageDocumentScannedCallback,
        onPDFDocumentScanned: OnPDFDocumentScannedCallback,
        onException: OnTaskFailedCallback
    ) {
        val documentScannerLauncher = activity
            .registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    GmsDocumentScanningResult
                        .fromActivityResultIntent(result.data)
                        ?.let { scanResult ->
                            scanResult.pages?.let { onPageDocumentScanned.invoke(it) }
                            scanResult.pdf?.let { onPDFDocumentScanned.invoke(it) }
                        }
                }
            }

        GmsDocumentScanning.getClient(documentScannerOptions)
            .getStartScanIntent(activity)
            .addOnSuccessListener { intentSender ->
                documentScannerLauncher.launch(
                    IntentSenderRequest.Builder(intentSender).build()
                )
            }
            .addOnFailureListener { onException.invoke(it) }
    }
}