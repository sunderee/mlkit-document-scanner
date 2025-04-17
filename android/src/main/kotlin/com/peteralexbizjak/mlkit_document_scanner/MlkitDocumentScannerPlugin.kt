package com.peteralexbizjak.mlkit_document_scanner

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

@ExperimentalStdlibApi
class MlkitDocumentScannerPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private var library: MlkitDocumentScannerLibrary = MlkitDocumentScannerLibrary()

    private lateinit var channel: MethodChannel
    private lateinit var eventChannelJPEG: EventChannel
    private lateinit var eventChannelPDF: EventChannel

    private var activity: Activity? = null
    private var eventSinkJPEG: EventChannel.EventSink? = null
    private var eventSinkPDF: EventChannel.EventSink? = null
    private var documentScannerLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, METHOD_CHANNEL)
        eventChannelJPEG = EventChannel(flutterPluginBinding.binaryMessenger, EVENT_CHANNEL_JPEG)
        eventChannelPDF = EventChannel(flutterPluginBinding.binaryMessenger, EVENT_CHANNEL_PDF)

        channel.setMethodCallHandler(this)
        eventChannelJPEG.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                eventSinkJPEG = events
            }

            override fun onCancel(arguments: Any?) {
                eventSinkJPEG = null
            }
        })
        eventChannelPDF.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                eventSinkPDF = events
            }

            override fun onCancel(arguments: Any?) {
                eventSinkPDF = null
            }
        })
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        Log.d(LOGGING_TAG, "onAttachedToActivity called")
        activity = binding.activity

        if (activity is ComponentActivity) {
            Log.d(LOGGING_TAG, "Initializing documentScannerLauncher")
            documentScannerLauncher = (activity as ComponentActivity)
                .registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                    if (result.resultCode == RESULT_OK) {
                        library.handleActivityResult(result.data, eventSinkJPEG, eventSinkPDF)
                    }
                }
        } else {
            Log.e(LOGGING_TAG, "Activity is not an instance of ComponentActivity")
        }
    }


    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        Log.d(LOGGING_TAG, "onMethodCall called for ${call.method}")

        if (call.method == START_DOCUMENT_SCANNER) {
            Log.d(LOGGING_TAG, call.arguments.toString())

            // Construct document scanner
            val scanner = library.buildGmsDocumentScanner(
                maximumNumberOfPages = call.argument<Int>(ARGUMENT_NUMBER_OF_PAGES) ?: 1,
                galleryImportAllowed = call.argument<Boolean>(ARGUMENT_GALLERY_IMPORT_ALLOWED) == true,
                scannerMode = call.argument<Int>(ARGUMENT_SCANNER_MODE)?.let {
                    val allowedScannerModes = listOf(
                        GmsDocumentScannerOptions.SCANNER_MODE_FULL,
                        GmsDocumentScannerOptions.SCANNER_MODE_BASE,
                        GmsDocumentScannerOptions.SCANNER_MODE_BASE_WITH_FILTER
                    )
                    if (allowedScannerModes.contains(it)) it else GmsDocumentScannerOptions.SCANNER_MODE_FULL
                } ?: GmsDocumentScannerOptions.SCANNER_MODE_FULL,
                resultMode = DocumentScannerResultMode.entries[call.argument<Int>(
                    ARGUMENT_RESULT_MODE
                ) ?: 2],
            )

            // Launch document scanner activity, and collect failure if it occurs
            activity?.let {
                scanner
                    .getStartScanIntent(it)
                    .addOnSuccessListener { intentSender ->
                        Log.d(LOGGING_TAG, "Launching document scanner")
                        documentScannerLauncher?.launch(
                            IntentSenderRequest.Builder(intentSender).build()
                        )
                    }
                    .addOnFailureListener { exception ->
                        Log.e(LOGGING_TAG, "MLKit threw exception:\n" + exception.message)
                        result.error(
                            ERROR_CODE_START_SCAN_INTENT_FAILURE,
                            ERROR_MESSAGE_START_SCAN_INTENT_FAILURE,
                            exception
                        )
                    }
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }
}
