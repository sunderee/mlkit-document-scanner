import 'dart:typed_data';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'mlkit_document_scanner_method_channel.dart';

abstract class MLKitDocumentScannerPlatform extends PlatformInterface {
  static final Object _token = Object();

  MLKitDocumentScannerPlatform() : super(token: _token);

  static MLKitDocumentScannerPlatform _instance =
      MethodChannelMLKitDocumentScanner();

  static MLKitDocumentScannerPlatform get instance => _instance;

  static set instance(MLKitDocumentScannerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<Uint8List?> initDocumentScannerAndReceivePDFBytes({
    int? maximumNumberOfPages,
    bool? galleryImportAllowed,
    int? scannerMode,
  }) {
    throw UnimplementedError(
      'initDocumentScannerAndReceivePDFBytes() has not been implemented.',
    );
  }
}
