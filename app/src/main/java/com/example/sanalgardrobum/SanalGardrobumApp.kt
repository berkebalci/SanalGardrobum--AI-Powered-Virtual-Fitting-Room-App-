package com.example.sanalgardrobum

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Uygulama genelinde Hilt DI container'ını başlatan Application sınıfı.
 *
 * @HiltAndroidApp, Hilt'in kod üretimini tetikler ve uygulama düzeyindeki
 * bağımlılık grafiğini (component) oluşturur. AndroidManifest'te
 * android:name ile kayıtlı olmalıdır.
 */
@HiltAndroidApp
class SanalGardrobumApp : Application()
