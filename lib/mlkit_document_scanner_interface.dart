import 'dart:typed_data';

import 'package:mlkit_document_scanner/mlkit_document_scanner_enums.dart';

/// This is the main plugin class. You should be able to instantiate the class,
/// use the [startDocumentScanner] method to configure and start a scanner,
/// and collect PDF scan data from the [scanResults] stream.
abstract interface class MlkitDocumentScanner {
  /// This stream contains full stream of JPEG data that came from the scan.
  Stream<List<Uint8List>?> get jpegScanResults;

  /// This stream contains full stream of PDF data that came from the scan.
  Stream<Uint8List?> get pdfScanResults;

  /// This method starts the document scanner. It tries to mimic the official
  /// API as much as possible. Refer to this URL for more information:
  /// https://developers.google.com/ml-kit/vision/doc-scanner.
  ///
  /// - [maximumNumberOfPages] sets the limit to the number of pages scanned.
  /// - [galleryImportAllowed] enable or disable the capability to import from
  /// the photo gallery.
  /// - [scannerMode] customize the editing functionalities available to the
  /// user by choosing from 3 modes available in [MlkitDocumentScannerMode].
  /// - [resultMode] sets the result format, this is, either JPEG pages or a
  /// single PDF document. This will determine which stream ([jpegScanResults]
  /// or [pdfScanResults]) will receive updates.
  Future<void> startDocumentScanner({
    required int maximumNumberOfPages,
    required bool galleryImportAllowed,
    required MlkitDocumentScannerMode scannerMode,
    required DocumentScannerResultMode resultMode,
  });
}
