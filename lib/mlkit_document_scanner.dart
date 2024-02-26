import 'mlkit_document_scanner_platform_interface.dart';

class MlkitDocumentScanner {
  Future<String?> getPlatformVersion() {
    return MlkitDocumentScannerPlatform.instance.getPlatformVersion();
  }
}
