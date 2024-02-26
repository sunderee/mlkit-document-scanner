import 'mlkit_document_scanner_platform_interface.dart';

class MLKitDocumentScanner {
  Future<String?> getPlatformVersion() {
    return MLKitDocumentScannerPlatform.instance.getPlatformVersion();
  }
}
