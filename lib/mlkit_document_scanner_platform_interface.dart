import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'mlkit_document_scanner_method_channel.dart';

abstract class MLKitDocumentScannerPlatform extends PlatformInterface {
  /// Constructs a MLKitDocumentScannerPlatform.
  MLKitDocumentScannerPlatform() : super(token: _token);

  static final Object _token = Object();

  static MLKitDocumentScannerPlatform _instance =
      MethodChannelMLKitDocumentScanner();

  /// The default instance of [MLKitDocumentScannerPlatform] to use.
  ///
  /// Defaults to [MethodChannelMLKitDocumentScanner].
  static MLKitDocumentScannerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MLKitDocumentScannerPlatform] when
  /// they register themselves.
  static set instance(MLKitDocumentScannerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
