package com.kerumitbsl.testtasknatife.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.databinding.MainItemBinding
import com.kerumitbsl.testtasknatife.extensions.loadGifFromGIPHY
import java.util.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>(), Filterable {

    val contentList = mutableListOf<GifObject>()
    private val permanentContentList = mutableListOf<GifObject>()

    var itemClick: (GifObject) -> Unit = {}

    @SuppressLint("NotifyDataSetChanged")
    fun setContent(list: List<GifObject>) {
        clearContent()
        contentList.addAll(list)
        permanentContentList.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFilteredContent(list: List<GifObject>) {
        contentList.clear()
        contentList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearContent() {
        permanentContentList.clear()
        contentList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val binder = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        loadGifFromGIPHY(contentList[position], holder.binding.gifRepresentationImageView)
        holder.binding.gifRepresentationImageView.setOnClickListener {
            itemClick(contentList[position])
        }
    }

    override fun getItemCount(): Int = contentList.size

    class MyViewHolder(val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString: String = constraint.toString()

                var filteredList = mutableListOf<GifObject>()

                if (charString.isEmpty()) {
                    filteredList = permanentContentList
                } else {
                    for (item in permanentContentList) {
                        if (item.title.lowercase(Locale.getDefault()).contains(charString)) {
                            filteredList.add(item)
                        }
                    }
                }

                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                setFilteredContent(results?.values as List<GifObject>)
            }
        }
    }
}