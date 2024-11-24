# MLKit Document Scanner

Flutter plugin bringing [MLKit Document Scanner](https://developers.google.com/ml-kit/vision/doc-scanner)
to Flutter.

**Important notice:** MLKit Document Scanner itself is in beta, and it's only available on Android. This package is also in early stages of development, so many things can change. Will do my best to document these potential changes in CHANGELOG.

## Usage

First, change the `MainActivity` to extends `FlutterFragmentActivity`:

```kt
import io.flutter.embedding.android.FlutterFragmentActivity

class MainActivity: FlutterFragmentActivity()
```

Optionally, you can add the following two `meta-data` tags in your `AndroidManifest.xml` (above `activity`, inside `application`):

```xml
<meta-data
    android:name="com.google.android.gms.version"
    android:value="@integer/google_play_services_version" />

<meta-data
    android:name="com.google.mlkit.vision.DEPENDENCIES"
    android:value="document_ui"/>
```

First metadata tag embeds the version of Google Play services that the app was compiled with, and the second one configures the app to automatically download the model to the device after installation.

To use the plugin, first create an instance of `MlkitDocumentScannerPlugin`...

```dart
final _mlkitDocumentScannerPlugin = MlkitDocumentScannerPlugin();
```

...and to launch the document scanner activity, call `startDocumentScanner` method (all four arguments are required):

```dart
_mlkitDocumentScannerPlugin.startDocumentScanner(
    maximumNumberOfPages: 1,
    galleryImportAllowed: true,
    scannerMode: MlkitDocumentScannerMode.full,
    resultMode: DocumentScannerResultMode.both,
);
```

This plugin attempts to follow the API of the MLKit Document Scanner as close as possible. For more information on what each parameter in `startDocumentScanner` does, please refer to [our documentation](https://pub.dev/documentation/mlkit_document_scanner/latest/mlkit_document_scanner/MlkitDocumentScannerPlugin/startDocumentScanner.html).

Depending on the `resultMode` set, you have two streams that can receive scan data:

```dart
// Stream<List<Uint8List>?>
_mlkitDocumentScannerPlugin.jpegScanResults;

// Stream<Uint8List?>
_mlkitDocumentScannerPlugin.pdfScanResults;
```

## Contribution

As mentioned above, plugin is under active development, and any kind of help is welcome. Feel free to [create a new issue on GitHub](https://github.com/sunderee/mlkit-document-scanner/issues) if you encounter any problems.