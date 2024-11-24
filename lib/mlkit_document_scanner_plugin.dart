import 'package:flutter/services.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner_enums.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner_interface.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

/// Implementation of the [MlkitDocumentScanner] interface.
final class MlkitDocumentScannerPlugin extends PlatformInterface
    implements MlkitDocumentScanner {
  static const _methodChannel =
      MethodChannel('mlkit_document_scanner_method_channel');
  static const _eventChannelJPEG =
      EventChannel('mlkit_document_scanner_event_channel_jpeg');
  static const _eventChannelPDF =
      EventChannel('mlkit_document_scanner_event_channel_pdf');

  MlkitDocumentScannerPlugin({required super.token});

  /// This stream contains full stream of JPEG data that came from the scan.
  @override
  Stream<List<Uint8List>?> get jpegScanResults {
    return _eventChannelJPEG
        .receiveBroadcastStream()
        .map((event) => (event as List<dynamic>).cast<Uint8List>());
  }

  /// This stream contains full stream of PDF data that came from the scan.
  @override
  Stream<Uint8List?> get pdfScanResults {
    return _eventChannelPDF
        .receiveBroadcastStream()
        .map((event) => event as Uint8List);
  }

  /// This method starts the document scanner. It tries to mimic the official
  /// API as much as possible. Refer to this URL for more information:
  /// https://developers.google.com/ml-kit/vision/doc-scanner.
  ///
  /// - [maximumNumberOfPages] sets the limit to the number of pages scanned.
  /// - [galleryImportAllowed] enable or disable the capability to import from
  /// the photo gallery.
  /// - [scannerMode] customize the editing functionalities available to the
  /// user by choosing from 3 modes available in [MlkitDocumentScannerMode].
  /// - [resultMode] sets the result format, this is, either JPEG pages or a
  /// single PDF document. This will determine which stream ([jpegScanResults]
  /// or [pdfScanResults]) will receive updates.
  @override
  Future<void> startDocumentScanner({
    required int maximumNumberOfPages,
    required bool galleryImportAllowed,
    required MlkitDocumentScannerMode scannerMode,
    required DocumentScannerResultMode resultMode,
  }) async {
    await _methodChannel.invokeMethod('startDocumentScanner', {
      'maximumNumberOfPages': maximumNumberOfPages,
      'galleryImportAllowed': galleryImportAllowed,
      'scannerMode': scannerMode.code,
      'resultMode': resultMode.code,
    });
  }
}
