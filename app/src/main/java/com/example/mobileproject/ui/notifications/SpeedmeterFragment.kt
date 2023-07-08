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
      binding.speedMeterSim1.prepareGauge(requireContext())
      binding.speedMeterSim1.addGaugeListener(object : SuperGaugeView.GaugeListener{
          override fun onProgress(progress: Float) {
          }

          @RequiresApi(Build.VERSION_CODES.Q)
          override fun onGaugePrepared(prepared: Boolean) {
              if(prepared){
                  CoroutineScope(Dispatchers.Main).launch {
                      while(true){
                          delay(1000)
                          val progress = GetSpeedValue()
                          if (progress != null) {
                              binding.speedMeterSim1.setProgress(progress.dbm.toFloat()+(196..204).random())
                              binding.speedMeterSim1.setGaugeText(progress.dbm.toString() + " dbm")
                          }
                      }
                  }
              }
          }

      })
      adapter = LTECellAdapter(requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
      return root
  }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun GetSpeedValue(): CellSignalStrength? {
        viewModel.fetchLTECellInfo()
        val regSimInfo = viewModel.fetchSignalStrenght()
        return regSimInfo
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the LTE cell info from the ViewModel
        viewModel.lteCellInfo.observe(viewLifecycleOwner) { cellList ->
            // Update the adapter with the new list of LTE cells
            adapter.setLTECellList(cellList)
        }
        // Fetch the LTE cell info
        viewModel.fetchLTECellInfo()
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}