package com.example.cov_x.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.cov_x.adapters.FotoAdapter.FotoViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.cov_x.R
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FotoAdapter(val listFotoImage: ArrayList<Uri>, val listNamaFoto: ArrayList<String>) : RecyclerView.Adapter<FotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_foto, parent, false)
        return FotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: FotoViewHolder, position: Int) {
        val image = listFotoImage.get(position)
        val namaFoto = listNamaFoto.get(position)
        Glide.with(holder.itemView.context)
                .load(image)
                .apply(RequestOptions())
                .into(holder.imageView)
        holder.textFoto.text = namaFoto
    }

    override fun getItemCount(): Int {
        return listFotoImage.size
    }

    class FotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_foto)
        val textFoto: TextView = itemView.findViewById(R.id.textNama)
    }
}