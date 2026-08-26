package com.example.sanalgardrobum.data.util

import android.content.Context
import androidx.camera.core.CameraEffect.IMAGE_CAPTURE
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun PreviewCamera(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val controller = remember {
        LifecycleCameraController(context.applicationContext).apply{
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )

        }
    }.apply { bindToLifecycle(lifecycleOwner) }

    AndroidView(
        factory ={
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier)


}