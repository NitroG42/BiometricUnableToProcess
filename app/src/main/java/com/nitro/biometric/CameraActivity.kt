package com.nitro.biometric

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_camera.*

/**
 * Created by t.coulange on 20/07/2020.
 */
class CameraActivity : AppCompatActivity(R.layout.activity_camera) {
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCamera()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            startCamera()
        }
        authenticate.setOnClickListener {
            startActivity(Intent(applicationContext, PromptActivity::class.java))
        }
    }


    private fun startCamera() {
        ProcessCameraProvider.getInstance(applicationContext).apply {
            addListener(
                cameraProviderListener(get()),
                ContextCompat.getMainExecutor(applicationContext)
            )
        }
    }

    private fun getPreview(): Preview {
        return Preview.Builder().build().also {
            it.setSurfaceProvider(preview.createSurfaceProvider())
        }
    }

    private fun cameraProviderListener(cameraProvider: ProcessCameraProvider) = Runnable {
        val preview = getPreview()
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        try {
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview
            )
        } catch (exc: Exception) {

        }
    }
}