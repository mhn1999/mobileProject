package com.example.mobileproject

import android.annotation.SuppressLint
import android.os.Build
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class LTECellAdapter(private val telephonyManager: TelephonyManager) : RecyclerView.Adapter<LTECellAdapter.LTECellViewHolder>() {

    private var lteCellList: List<CellInfo> = emptyList()

    fun setLTECellList(cells: List<CellInfo>) {
        lteCellList = cells
        notifyDataSetChanged()
    }
    fun ConnectToCell(cell: CellInfo){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LTECellViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_info, parent, false)
        return LTECellViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: LTECellViewHolder, position: Int) {
        val lteCell = lteCellList[position]
        // Bind the LTE cell info to the views in the ViewHolder
        holder.cellInfoTextView.text = lteCell.toString()
//        holder.cellInfoButton.setOnClickListener {
//        val flag =telephonyManager.setNetworkSelectionModeManual(lteCell.cellIdentity.toString(),false)
//            if (flag==true)
//            {
//                holder.cellInfoButton.text="shit"
//            }
//        }

    }

    override fun getItemCount(): Int = lteCellList.size

    inner class LTECellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellInfoTextView: TextView = itemView.findViewById(R.id.cellInfoTextView)
        val cellInfoButton: Button = itemView.findViewById(R.id.cellInfoButton)
    }
}
