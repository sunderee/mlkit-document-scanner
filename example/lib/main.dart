import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:mlkit_document_scanner/mlkit_document_scanner.dart';
import 'package:syncfusion_flutter_pdfviewer/pdfviewer.dart';

void main() {
  runApp(const MyApp());
}

final class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

final class _MyAppState extends State<MyApp> {
  final MlkitDocumentScannerPlugin _mlkitDocumentScannerPlugin =
      MlkitDocumentScannerPlugin(token: Object());
  late final Stream<List<Uint8List>?> _jpegDocumentStream;
  late final Stream<Uint8List?> _pdfDocumentStream;

  @override
  void initState() {
    super.initState();
    _jpegDocumentStream = _mlkitDocumentScannerPlugin.jpegScanResults;
    _pdfDocumentStream = _mlkitDocumentScannerPlugin.pdfScanResults;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: SafeArea(
          minimum: const EdgeInsets.all(16.0),
          child: ListView(
            children: [
              TextButton(
                onPressed: () =>
                    _mlkitDocumentScannerPlugin.startDocumentScanner(
                      maximumNumberOfPages: 1,
                      galleryImportAllowed: true,
                      scannerMode: MlkitDocumentScannerMode.full,
                      resultMode: DocumentScannerResultMode.both,
                    ),
                child: const Text('Start scanner'),
              ),
              StreamBuilder(
                stream: _jpegDocumentStream,
                builder:
                    (
                      BuildContext context,
                      AsyncSnapshot<List<Uint8List>?> data,
                    ) {
                      return data.hasData && data.data != null
                          ? ListView(
                              shrinkWrap: true,
                              children: data.data!
                                  .map((item) => Image.memory(item))
                                  .toList(),
                            )
                          : const Text('No JPEG data yet');
                    },
              ),
              StreamBuilder(
                stream: _pdfDocumentStream,
                builder:
                    (BuildContext context, AsyncSnapshot<Uint8List?> data) {
                      return data.hasData && data.data != null
                          ? SizedBox(
                              height: MediaQuery.of(context).size.height - 32,
                              child: SfPdfViewer.memory(data.data!),
                            )
                          : const Text('No PDF data yet');
                    },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
