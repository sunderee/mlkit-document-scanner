/// Options that the MLKit Document Scanner allows.
enum MlkitDocumentScannerMode {
  /// Adds ML-enabled image cleaning capabilities (erase stains, fingers, etc…)
  /// to the [MlkitDocumentScannerMode.baseWithFilter] mode. This mode will also
  /// allow future major features to be automatically added along with Google
  /// Play services updates, while the other two modes will maintain their
  /// current feature sets and only receive minor refinements.
  full(1),

  /// Basic editing capabilities (crop, rotate, reorder pages, etc…).
  base(3),

  /// Adds image filters (gray scale, auto image enhancement, etc…) to the
  /// [MlkitDocumentScannerMode.base] mode.
  baseWithFilter(2);

  /// Only used internally.
  final int code;

  const MlkitDocumentScannerMode(this.code);
}

/// Options on which stream should receive updates.
enum DocumentScannerResultMode {
  /// Only send updates to [MlkitDocumentScannerPlugin.jpegScanResults].
  jpegPages(0),

  /// Only send updates to [MlkitDocumentScannerPlugin.pdfScanResults].
  pdfFile(1),

  /// Send updates to both [MlkitDocumentScannerPlugin.jpegScanResults] and
  /// [MlkitDocumentScannerPlugin.pdfScanResults]. Note that using this option
  /// may negatively impact performance.
  both(2);

  /// Only used internally.
  final int code;

  const DocumentScannerResultMode(this.code);
}
