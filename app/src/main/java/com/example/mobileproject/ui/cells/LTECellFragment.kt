package com.example.mobileproject.ui.cells

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileproject.LTECellAdapter
import com.example.mobileproject.databinding.FragmentCellsBinding

class LTECellFragment : Fragment() {
    private var _binding: FragmentCellsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LTECellViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LTECellAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Initialize ViewModel
            viewModel = ViewModelProvider(
                this,
                ViewModelFactory(requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
            ).get(LTECellViewModel::class.java)
        _binding = FragmentCellsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.recyclerView
        adapter = LTECellAdapter(requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter


        return root
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
}
