import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'mlkit_document_scanner_method_channel.dart';

abstract class MlkitDocumentScannerPlatform extends PlatformInterface {
  /// Constructs a MlkitDocumentScannerPlatform.
  MlkitDocumentScannerPlatform() : super(token: _token);

  static final Object _token = Object();

  static MlkitDocumentScannerPlatform _instance =
      MethodChannelMlkitDocumentScanner();

  /// The default instance of [MlkitDocumentScannerPlatform] to use.
  ///
  /// Defaults to [MethodChannelMlkitDocumentScanner].
  static MlkitDocumentScannerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MlkitDocumentScannerPlatform] when
  /// they register themselves.
  static set instance(MlkitDocumentScannerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
