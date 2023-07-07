//package com.example.mobileproject
//
//import android.R
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import androidx.activity.result.ActivityResultCallback
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.google.android.material.snackbar.Snackbar
//
//
//class MainActivity : AppCompatActivity() {
//    val PERMISSIONS = arrayOf<String>(
//        Manifest.permission.FOREGROUND_SERVICE,
//        Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
//        Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
//    )
//    private var multiplePermissionsContract: RequestMultiplePermissions? = null
//    private var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        multiplePermissionsContract = RequestMultiplePermissions()
//        multiplePermissionLauncher = registerForActivityResult<Array<String>, Map<String, Boolean>>(
//            multiplePermissionsContract,
//            ActivityResultCallback<Map<String?, Boolean?>> { isGranted: Map<String?, Boolean?> ->
//                Log.d("PERMISSIONS", "Launcher result: $isGranted")
//                if (isGranted.containsValue(false)) {
//                    Log.d(
//                        "PERMISSIONS",
//                        "At least one of the permissions was not granted, launching again..."
//                    )
//                    multiplePermissionLauncher!!.launch(PERMISSIONS)
//                }
//            })
//        askPermissions(multiplePermissionLauncher!!)
//    }
//
//    private fun askPermissions(multiplePermissionLauncher: ActivityResultLauncher<Array<String>>) {
//        if (!hasPermissions(PERMISSIONS)) {
//            Log.d(
//                "PERMISSIONS",
//                "Launching multiple contract permission launcher for ALL required permissions"
//            )
//            multiplePermissionLauncher.launch(PERMISSIONS)
//        } else {
//            Log.d("PERMISSIONS", "All permissions are already granted")
//        }
//    }
//
//    private fun hasPermissions(permissions: Array<String>?): Boolean {
//        if (permissions != null) {
//            for (permission in permissions) {
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        permission
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    Log.d("PERMISSIONS", "Permission is not granted: $permission")
//                    return false
//                }
//                Log.d("PERMISSIONS", "Permission already granted: $permission")
//            }
//            return true
//        }
//        return false
//    }
//}
//
//
//
//
//private val requestPermissionLauncher =
//    registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            Log.i("Permission: ", "Granted")
//        } else {
//            Log.i("Permission: ", "Denied")
//        }
//    }
//
//fun onClickRequestPermission(view: View) {
//    when {
//        ContextCompat.checkSelfPermission(
//            this,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED -> {
//            layout.showSnackbar(
//                view,
//                getString(com.example.mobileproject.R.string.permission_granted),
//                Snackbar.LENGTH_INDEFINITE,
//                null
//            ) {}
//        }
//
//        ActivityCompat.shouldShowRequestPermissionRationale(
//            this,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        ) -> {
//            layout.showSnackbar(
//                view,
//                getString(com.example.mobileproject.R.string.permission_required),
//                Snackbar.LENGTH_INDEFINITE,
//                getString(com.example.mobileproject.R.string.ok)
//            ) {
//                requestPermissionLauncher.launch(
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION
//                )
//            }
//        }
//
//        else -> {
//            requestPermissionLauncher.launch(
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            )
//        }
//    }
//}
//}
//fun View.showSnackbar(
//    view: View,
//    msg: String,
//    length: Int,
//    actionMessage: CharSequence?,
//    action: (View) -> Unit
//) {
//    val snackbar = Snackbar.make(view, msg, length)
//    if (actionMessage != null) {
//        snackbar.setAction(actionMessage) {
//            action(this)
//        }.show()
//    } else {
//        snackbar.show()
//    }
//}