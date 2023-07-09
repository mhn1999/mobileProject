package com.example.mobileproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileproject.R
import com.example.mobileproject.databinding.FragmentHomeBinding
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.view.menu.MenuView.ItemView
import com.example.mobileproject.HowToUseAct
import kotlinx.coroutines.NonDisposableHandle.parent

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root
      val titleView: TextView = binding.textHome
      val advancedSettingsButton :Button = binding.AdvancedSettingButton
      val HowToUseButton :Button = binding.HowToUseButton
      var toggle = true
      // What happens when the
      // TextView in clicked/tapped
      titleView.setOnClickListener {

          // If toggle is true, then text will become
          // white and background will become green
          // Else text is green and background is white
          if (toggle) {
              titleView.setTextAppearance(R.style.whiteText)
              titleView.setBackgroundResource(R.color.purple_200)
          } else {
              titleView.setTextAppearance(R.style.gfgGreenText)
              titleView.setBackgroundResource(R.color.white)
          }
          toggle = !toggle
      }
          advancedSettingsButton.setOnClickListener{
              val intent = Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)//Settings.ACTION_NETWORK_OPERATOR_SETTINGS)//Global.NETWORK_PREFERENCE)//ACTION_DEVICE_INFO_SETTINGS
              startActivity(intent)
          }
      HowToUseButton.setOnClickListener {
          val intent = Intent(context, HowToUseAct::class.java)
          startActivity(intent)
      }

          // Logically inversing the toggle, i.e. if toggle
          // is true then it shall become false
          // And vice-versa to keep the styles
          // keep changing on every click/tap


//    homeViewModel.text.observe(viewLifecycleOwner) {
//        titleView.text = it
//    }
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}