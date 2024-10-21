package com.islam.moveitemsbetweenrv

import android.content.ClipData
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islam.moveitemsbetweenrv.databinding.ListRowBinding

class ListAdapter(
    private var list: List<String>,
    private val listener: Listener
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>(), View.OnTouchListener {

    // ViewHolder class with ViewBinding
    inner class ListViewHolder(val binding: ListRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = list[position]
        holder.binding.text.text = item
        holder.binding.frameLayoutItem.tag = position
        holder.binding.frameLayoutItem.setOnTouchListener(this)
        holder.binding.frameLayoutItem.setOnDragListener(DragListener(listener))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    @Suppress("DEPRECATION")
                    v.startDrag(data, shadowBuilder, v, 0)
                }
                return true
            }
        }
        return false
    }

    // Function to get the list
    fun getList(): List<String> {
        return list
    }

    // Function to update the list
    fun updateList(newList: List<String>) {
        this.list = newList
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    // Function to get the DragListener instance
    fun getDragInstance(): DragListener? {
        return if (listener != null) {
            DragListener(listener)
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!")
            null
        }
    }
}
