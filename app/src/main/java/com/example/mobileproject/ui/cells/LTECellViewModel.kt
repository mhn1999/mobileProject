package com.example.mobileproject.ui.cells

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.telephony.*
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel

class LTECellViewModel(private val context: TelephonyManager) : ViewModel() {

    private val _lteCellInfo = MutableLiveData<List<CellInfo>>()
    val lteCellInfo: LiveData<List<CellInfo>> get() = _lteCellInfo

    fun fetchLTECellInfo() {
////        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//            if (ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
                val cellInfoList = context.allCellInfo
                _lteCellInfo.value = cellInfoList
//            }
        }
    }