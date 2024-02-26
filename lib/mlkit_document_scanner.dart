import 'dart:typed_data';

import 'mlkit_document_scanner_platform_interface.dart';

class MLKitDocumentScanner {
  Future<Uint8List?> initDocumentScanner() {
    return MLKitDocumentScannerPlatform.instance
        .initDocumentScannerAndReceivePDFBytes();
  }
}
