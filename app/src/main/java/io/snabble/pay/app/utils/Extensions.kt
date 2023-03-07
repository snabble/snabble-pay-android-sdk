package io.snabble.pay.app.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

fun Context.getMetaDataKey(key: String) =
    if (containsMetaDataKey(key)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager
                .getApplicationInfo(
                    packageName,
                    PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
                )
                .metaData
                .getString(key) ?: ""
        } else {
            @Suppress("DEPRECATION")
            packageManager
                .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
                .metaData
                .getString(key) ?: ""
        }
    } else {
        ""
    }

fun Context.containsMetaDataKey(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager
            .getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
            )
            .metaData
            .containsKey(key)
    } else {
        @Suppress("DEPRECATION")
        packageManager
            .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            .metaData
            .containsKey(key)
    }
