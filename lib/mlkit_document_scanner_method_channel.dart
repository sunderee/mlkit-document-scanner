import 'package:flutter/services.dart';

import 'mlkit_document_scanner_platform_interface.dart';

class MethodChannelMLKitDocumentScanner extends MLKitDocumentScannerPlatform {
  final MethodChannel _methodChannel;

  MethodChannelMLKitDocumentScanner()
      : _methodChannel = const MethodChannel('mlkit_document_scanner');

  @override
  Future<Uint8List?> initDocumentScannerAndReceivePDFBytes({
    int? maximumNumberOfPages,
    bool? galleryImportAllowed,
    int? scannerMode,
  }) async {
    return _methodChannel.invokeMethod<Uint8List>(
      'initDocumentScannerAndReceivePDFBytes',
      {
        'maximumNumberOfPages': maximumNumberOfPages,
        'galleryImportAllowed': galleryImportAllowed,
        'scannerMode': scannerMode
      },
    );
  }
}
