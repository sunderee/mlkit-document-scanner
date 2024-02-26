import 'package:flutter_test/flutter_test.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner_method_channel.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner_platform_interface.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockMlkitDocumentScannerPlatform
    with MockPlatformInterfaceMixin
    implements MlkitDocumentScannerPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final MlkitDocumentScannerPlatform initialPlatform =
      MlkitDocumentScannerPlatform.instance;

  test('$MethodChannelMlkitDocumentScanner is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelMlkitDocumentScanner>());
  });

  test('getPlatformVersion', () async {
    MlkitDocumentScanner mlkitDocumentScannerPlugin = MlkitDocumentScanner();
    MockMlkitDocumentScannerPlatform fakePlatform =
        MockMlkitDocumentScannerPlatform();
    MlkitDocumentScannerPlatform.instance = fakePlatform;

    expect(await mlkitDocumentScannerPlugin.getPlatformVersion(), '42');
  });
}
