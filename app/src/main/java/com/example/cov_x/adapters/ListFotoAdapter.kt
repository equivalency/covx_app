package com.example.cov_x.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cov_x.*
import com.example.cov_x.models.ListFotoPrediksiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFotoAdapter(val listFotoData: ArrayList<ListFotoPrediksiResponse>): RecyclerView.Adapter<ListFotoAdapter.ListFotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_foto, parent, false)
        return ListFotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListFotoViewHolder, position: Int) {
        val fotoData = listFotoData.get(position)
        Glide.with(holder.itemView.context)
                .load(fotoData.filepath)
                .apply(RequestOptions())
                .into(holder.fotoImage)
        holder.namaFoto.text = fotoData.filename
        holder.textTanggal.text = fotoData.created_at

        holder.btnLihat.setOnClickListener{
            val detailIntent = Intent(it.context, DetailPrediksiActivity::class.java)
            detailIntent.putExtra(DetailPrediksiActivity.FOTO_DATA, fotoData)
            it.context.startActivity(detailIntent)

        }
        holder.btnHapus.setOnClickListener{
            val builder1: AlertDialog.Builder = AlertDialog.Builder(it.context)
            builder1.setMessage("Apakah Anda yakin ingin menghapus data ini?")
            builder1.setCancelable(true)
            builder1.setPositiveButton(
                    "Ya",
                    DialogInterface.OnClickListener { dialog, _ ->
//                        val pref = it.context.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
//                        val request = ApiClient.buildService(UserService::class.java)
//                        val token = pref.getString(LoginActivity.KEY_TOKEN, "")
//                        val stringAuth = "Bearer " + token
//
//                        val call = fotoData.let { it1 -> request.hapusDataPrediksi(stringAuth, it1.filename) }
//
//                        call.enqueue(object : Callback<ResponseBody> {
//                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                                if (response.isSuccessful) {
//                                    deleteItem(position)
//                                    Toast.makeText(it.context, response.body().toString(), Toast.LENGTH_SHORT).show()
//                                } else {
//                                    Toast.makeText(it.context, "Terjadi kesalahan sistem\n Silakan Coba Lagi", Toast.LENGTH_LONG).show()
//                                }
//                            }
//
//                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                                Toast.makeText(it.context, "Terjadi kesalahan sistem: ${t.message}\nSilahkan Coba Lagi", Toast.LENGTH_LONG).show()
//                            }
//
//                        })
                        Toast.makeText(it.context, "Fitur ini belum ada. Hehe", Toast.LENGTH_LONG).show()
                        dialog.cancel()
                    })

            builder1.setNegativeButton(
                    "Tidak",
                    DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })

            val alert11: AlertDialog = builder1.create()
            alert11.show()
        }
    }

    private fun deleteItem(position: Int) {
        listFotoData.removeAt(position)
        notifyItemRemoved(position)
        listFotoData.let { notifyItemRangeChanged(position, it.size) }
    }

    override fun getItemCount(): Int {
        return listFotoData.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }



    inner class ListFotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fotoImage: ImageView = itemView.findViewById(R.id.img_avatar)
        val namaFoto: TextView = itemView.findViewById(R.id.txt_NamaFoto)
        val textTanggal: TextView = itemView.findViewById(R.id.textTanggal)

        val btnLihat: Button = itemView.findViewById(R.id.btnLihat)
        val btnHapus: Button = itemView.findViewById(R.id.btnHapus)
    }

}