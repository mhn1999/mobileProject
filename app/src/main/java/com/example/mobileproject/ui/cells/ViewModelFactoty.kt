package com.example.mobileproject.ui.cells

import android.content.Context
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val context: TelephonyManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LTECellViewModel(context) as T
    }
}
