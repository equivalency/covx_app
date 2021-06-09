package com.example.cov_x.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cov_x.*
import com.example.cov_x.adapters.ListFotoAdapter
import com.example.cov_x.models.ListFotoPrediksiResponse
import com.example.cov_x.models.PrediksiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RiwayatFragment : Fragment(){

    private lateinit var recycleView: RecyclerView
    private lateinit var spinLoader: ProgressBar
    private lateinit var emptyLayout: LinearLayout
    private lateinit var listFotoAdapter: ListFotoAdapter
    private var listFotoData: ArrayList<ListFotoPrediksiResponse> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleView = view.findViewById(R.id.rv_Foto)
        emptyLayout = view.findViewById(R.id.empty_layout)
        spinLoader = view.findViewById(R.id.ProgressBar01)

        listFotoAdapter = ListFotoAdapter(listFotoData)
        listFotoData.clear()

////        First state
        spinLoader.visibility = View.VISIBLE
        recycleView.visibility = View.GONE
//        State if no data
        getImageData()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    private fun setRecyclerView(){
        recycleView.setHasFixedSize(true)
        recycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listFotoAdapter
        }
        spinLoader.visibility = View.GONE
        recycleView.visibility = View.VISIBLE
    }

    private fun getImageData(){

        val pref = activity?.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
        val request = ApiClient.buildService(UserService::class.java)
        val token = pref?.getString(LoginActivity.KEY_TOKEN, "")
        val stringAuth = "Bearer " + token
        val call = request.getFotoHasil(stringAuth)
        call.enqueue(object: Callback<PrediksiResponse> {
            override fun onResponse(call: Call<PrediksiResponse>, response: Response<PrediksiResponse>){
                if (response.isSuccessful){
//                           Save Token
                    val items = response.body()?.data
                    if (items == null){
                        spinLoader.visibility = View.GONE
                        emptyLayout.visibility = View.VISIBLE
                        Toast.makeText(activity, "Tidak ada data", Toast.LENGTH_LONG).show()
                    }
                    else {


                        Toast.makeText(activity, "Jumlah Item: " + items.count(), Toast.LENGTH_LONG).show()
                        for (i in 0 until items.count()){
                            Log.d("FILENAME :", items[i].filename)
                            val filepath = ApiClient.baseURL + items[i].filepath
                            listFotoData.add(ListFotoPrediksiResponse(
                                    items[i].filename,
                                    filepath,
                                    items[i].prediction,
                                    items[i].status,
                                    items[i].created_at
                            ))
                        }
                        setRecyclerView()
                    }

                }
                else{
                    Toast.makeText(activity, "Terjadi kesalahan sistem\n Silakan Coba Lagi", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PrediksiResponse>, t: Throwable) {
                Toast.makeText(activity, "Terjadi kesalahan sistem: ${t.message}\nSilahkan Coba Lagi", Toast.LENGTH_LONG).show()
            }

        })
    }



}