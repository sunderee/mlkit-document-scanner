import 'package:flutter_test/flutter_test.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner_method_channel.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner_platform_interface.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockMLKitDocumentScannerPlatform
    with MockPlatformInterfaceMixin
    implements MLKitDocumentScannerPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final MLKitDocumentScannerPlatform initialPlatform =
      MLKitDocumentScannerPlatform.instance;

  test('$MethodChannelMLKitDocumentScanner is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelMLKitDocumentScanner>());
  });

  test('getPlatformVersion', () async {
    MLKitDocumentScanner mlkitDocumentScannerPlugin = MLKitDocumentScanner();
    MockMLKitDocumentScannerPlatform fakePlatform =
        MockMLKitDocumentScannerPlatform();
    MLKitDocumentScannerPlatform.instance = fakePlatform;

    expect(await mlkitDocumentScannerPlugin.getPlatformVersion(), '42');
  });
}
