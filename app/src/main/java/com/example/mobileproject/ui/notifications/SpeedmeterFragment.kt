package com.example.mobileproject.ui.notifications

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.CellInfo
import android.telephony.CellSignalStrength
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileproject.LTECellAdapter
import com.example.mobileproject.databinding.FragmentSpeedmeterBinding
import com.example.mobileproject.ui.cells.LTECellViewModel
import com.example.mobileproject.ui.cells.ViewModelFactory
import com.farimarwat.supergaugeview.SuperGaugeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpeedmeterFragment : Fragment() {
    private lateinit var viewModel: LTECellViewModel
    private lateinit var adapter: LTECellAdapter
    private var _binding: FragmentSpeedmeterBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  @RequiresApi(Build.VERSION_CODES.Q)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
      viewModel = ViewModelProvider(
          this,
          ViewModelFactory(requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
      ).get(LTECellViewModel::class.java)

    _binding = FragmentSpeedmeterBinding.inflate(inflater, container, false)
    val root: View = binding.root


      viewModel.fetchSignalStrenght()
      var progress = viewModel.registeredCellInfo
      if(progress.value!!.size >= 1) {
          binding.speedMeterSim2.setEnabled(false)
          binding.speedMeterSim1.prepareGauge(requireContext())
          binding.speedMeterSim1.addGaugeListener(object : SuperGaugeView.GaugeListener {
              override fun onProgress(progress: Float) {
              }

              @RequiresApi(Build.VERSION_CODES.R)
              override fun onGaugePrepared(prepared: Boolean) {
                  if (prepared) {
                      CoroutineScope(Dispatchers.Main).launch {
                          while (true) {
                              viewModel.fetchSignalStrenght()
                              delay(1000)
                              progress = viewModel.registeredCellInfo
                              val dbmLevel =
                                  progress.value?.get(0)?.cellSignalStrength?.dbm!!.toFloat()
                              binding.speedMeterSim1.setProgress(dbmLevel + (196..204).random())
                              binding.speedMeterSim1.setGaugeText(dbmLevel.toString() + " dbm")

                          }
                      }

                  }
              }

          })
      }
      if(progress.value!!.size == 2) {
        binding.speedMeterSim2.setEnabled(true)
          binding.speedMeterSim2.prepareGauge(requireContext())
          binding.speedMeterSim2.addGaugeListener(object : SuperGaugeView.GaugeListener {
              override fun onProgress(progress: Float) {
              }

              @RequiresApi(Build.VERSION_CODES.R)
              override fun onGaugePrepared(prepared: Boolean) {
                  if (prepared) {
                      CoroutineScope(Dispatchers.Main).launch {
                          while (true) {
                              viewModel.fetchSignalStrenght()
                              delay(1000)
                              progress = viewModel.registeredCellInfo
                              val dbmLevel =
                                  progress.value?.get(1)?.cellSignalStrength?.dbm!!.toFloat()
                              binding.speedMeterSim2.setProgress(dbmLevel + (196..204).random())
                              binding.speedMeterSim2.setGaugeText(dbmLevel.toString() + " dbm")

                          }
                      }

                  }
              }

          })
      }

      adapter = LTECellAdapter(requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
      return root
  }


//override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}