import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'mlkit_document_scanner_platform_interface.dart';

/// An implementation of [MLKitDocumentScannerPlatform] that uses method channels.
class MethodChannelMLKitDocumentScanner extends MLKitDocumentScannerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('mlkit_document_scanner');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
