package com.peteralexbizjak.mlkit_document_scanner

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract

internal fun <I, O> Activity.registerForActivityResult(
    contract: ActivityResultContract<I, O>,
    callback: ActivityResultCallback<O>
) = (this as ComponentActivity).registerForActivityResult(contract, callback)