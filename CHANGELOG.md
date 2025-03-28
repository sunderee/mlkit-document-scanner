## 0.0.7

This change updates the Dart/Flutter SDK version constraints and dependencies. It also sets the new repository URL.

## 0.0.6

This change updates the Dart/Flutter SDK version constraints and dependencies.

## 0.0.5

This change updates the Dart/Flutter SDK version constraints and dependencies.

Since `MlkitDocumentScannerPlugin` is a final class, it cannot be mocked for testing purposes, so I have introduced the `MlkitDocumentScanner` interface.

## 0.0.4

This change open sources the project under a different license (from AGPL-3.0 to Apache License 2.0).

## 0.0.3

Minor configuration changes to the `build.gradle` and updates to the `README.md`.

## 0.0.2

Breaking change: `MlkitDocumentScannerPlugin` now introduces two streams, one of listening to JPEG and one for PDF results. In order to toggle which one of them should receive updates this version adds a new enum, `DocumentScannerResultMode`.

Add `topics` section to the `pubspec.yaml` file.

For more information on its usage, refer to the example and the API documentation.

## 0.0.1

This is the initial release of the plugin. Please beware of the fact that this code is *not* tested, and most likely *not* recommended for production use.
