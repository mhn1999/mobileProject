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
        holder.cellInfoTextView.text = lteCell.cellIdentity.operatorAlphaLong
        holder.cellInfoTextView1.text = lteCell.cellIdentity.toString()
        holder.cellInfoTextView2.text = lteCell.cellSignalStrength.toString()
        holder.cellInfoButton.setOnClickListener {
            setPreferredNetworkType()
        }

    }
    private fun setPreferredNetworkType() { //this is a fun u used to set network type in the past

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For API level 29 and above, use setPreferredNetworkType() directly
            //telephonyManager.setPreferredNetworkType(TelephonyManager.NETWORK_TYPE_LTE)
        } else {
            // For API level below 29, use reflection to access the hidden method
            try {
                val setPreferredNetworkTypeMethod =
                    telephonyManager.javaClass.getDeclaredMethod(
                        "setPreferredNetworkType",
                        Integer.TYPE
                    )
                setPreferredNetworkTypeMethod.invoke(
                    telephonyManager,
                    TelephonyManager.NETWORK_TYPE_LTE
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int = lteCellList.size

    inner class LTECellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellInfoTextView: TextView = itemView.findViewById(R.id.cellInfoTextView)
        val cellInfoTextView1: TextView = itemView.findViewById(R.id.cellInfoTextView1)
        val cellInfoTextView2: TextView = itemView.findViewById(R.id.cellInfoTextView2)
        val cellInfoButton: Button = itemView.findViewById(R.id.cellInfoButton)
    }
}
