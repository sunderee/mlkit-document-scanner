package com.peteralexbizjak.mlkit_document_scanner

import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.peteralexbizjak.mlkit_document_scanner.MLKitDocumentScanner

com.peteralexbizjak.mlkit_document_scanner.MLKitDocumentScannerConfiguration
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/** MLKitDocumentScannerPlugin */
class MLKitDocumentScannerPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var channel: MethodChannel
    private var flutterActivity: FlutterActivity? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "mlkit_document_scanner")
        channel.setMethodCallHandler(this)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        flutterActivity = binding.activity as FlutterActivity
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        flutterActivity = binding.activity as FlutterActivity
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == METHOD_INIT_DOCUMENT_SCANNER) {
            val arguments = parseArgumentsForInitializingDocumentScanner(call)

            flutterActivity?.activity?.let {
                MLKitDocumentScanner(
                    activity = it as AppCompatActivity,
                    maximumNumberOfPages = arguments.maximumNumberOfPages,
                    galleryImportAllowed = arguments.galleryImportAllowed,
                    scannerMode = arguments.scannerMode
                )
                    .beginListeningForDocumentScannerOutputs(
                        onPageDocumentScanned = { pages -> onPageDocumentScanned(result, pages) },
                        onPDFDocumentScanned = { pdf -> onPdfDocumentScanned(result, pdf) },
                        onException = { exception ->
                            result.error(
                                DOCUMENT_SCANNING_ERROR_CODE,
                                exception.message ?: "Error while listening for scanning updates",
                                exception
                            )
                        }
                    )
            }
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        flutterActivity = null
    }

    override fun onDetachedFromActivity() {
        flutterActivity = null
    }

    private fun parseArgumentsForInitializingDocumentScanner(call: MethodCall): MLKitDocumentScannerConfiguration {
        return MLKitDocumentScannerConfiguration(
            maximumNumberOfPages = call.argument<Int>(ARGUMENT_NUMBER_OF_PAGES) ?: 1,
            galleryImportAllowed = call.argument<Boolean>(ARGUMENT_GALLERY_IMPORT_ALLOWED) ?: false,
            scannerMode = call.argument<Int>(ARGUMENT_SCANNER_MODE)?.let {
                if (kotlin.collections.listOf(
                        GmsDocumentScannerOptions.SCANNER_MODE_FULL,
                        GmsDocumentScannerOptions.SCANNER_MODE_BASE,
                        GmsDocumentScannerOptions.SCANNER_MODE_BASE_WITH_FILTER
                    ).contains(it)
                ) it else GmsDocumentScannerOptions.SCANNER_MODE_FULL
            } ?: GmsDocumentScannerOptions.SCANNER_MODE_FULL
        )
    }

    private fun onPageDocumentScanned(
        result: Result,
        pages: List<GmsDocumentScanningResult.Page>
    ) {
    }

    private fun onPdfDocumentScanned(result: Result, pdf: GmsDocumentScanningResult.Pdf) {}

    companion object {
        private const val METHOD_INIT_DOCUMENT_SCANNER = "initDocumentScanner"
        private const val ARGUMENT_NUMBER_OF_PAGES = "maximumNumberOfPages"
        private const val ARGUMENT_GALLERY_IMPORT_ALLOWED = "galleryImportAllowed"
        private const val ARGUMENT_SCANNER_MODE = "scannerMode"

        private const val DOCUMENT_SCANNING_ERROR_CODE = "internal-document-scanning-error";
    }
}
