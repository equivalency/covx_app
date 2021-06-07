package com.example.cov_x

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cov_x.adapters.FotoAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class PreviewListFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvFoto: RecyclerView
    private lateinit var btnKirimFoto: Button
    private val loadingDialog = LoadingDialog(this)
    private var filePaths: ArrayList<String> = ArrayList()

    companion object {
        const val LIST_FOTO_URI = "list_foto_uri"
        const val LIST_NAMA_FOTO = "list_nama_foto"
        const val LIST_FILE_PATH = "list_file_path"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_list_foto)
        supportActionBar?.hide()

        rvFoto = findViewById(R.id.rv_fotoTampil)
        btnKirimFoto = findViewById(R.id.btn_KirimFoto)

        btnKirimFoto.setOnClickListener(this)


        rvFoto.setHasFixedSize(true)

        val listUri = intent.getSerializableExtra(LIST_FOTO_URI) as ArrayList<Uri>
        val listNamaFoto = intent.getStringArrayListExtra(LIST_NAMA_FOTO) as ArrayList<String>
        filePaths = intent.getStringArrayListExtra(LIST_FILE_PATH) as ArrayList<String>


        val fotoAdapter = FotoAdapter(listUri, listNamaFoto)
        rvFoto.apply {
            layoutManager = GridLayoutManager(this@PreviewListFotoActivity, 2)
            adapter = fotoAdapter
        }




    }


    private fun sendData(){

        val pref = this.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
        val request = ApiClient.buildService(UserService::class.java)
        val token = pref.getString(LoginActivity.KEY_TOKEN, "")
        val stringAuth = "Bearer " + token

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        val type = "multipart/form-data"
        for (i in filePaths.indices) {
            val file = File(filePaths[i])
            builder.addFormDataPart(
                "files[]",
                file.getName(),
                file.asRequestBody(type.toMediaTypeOrNull())
            )
        }

        val requestBody = builder.build()
        Log.d("REQUEST_BODY", requestBody.toString())
        val call = request.uploadImage(stringAuth, requestBody)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    loadingDialog.dismissDialog()
//                           Save Token
                    Toast.makeText(
                        this@PreviewListFotoActivity,
                        "Success " + response.message(),
                        Toast.LENGTH_LONG
                    ).show()
                    val successIntent = Intent(
                        this@PreviewListFotoActivity,
                        SuccessActivity::class.java
                    )
                    startActivity(successIntent)
                    finish()
                } else {
                    loadingDialog.dismissDialog()
                    Toast.makeText(
                        this@PreviewListFotoActivity,
                        "Terjadi kesalahan sistem\n Silakan Coba Lagi",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                loadingDialog.dismissDialog()
                Toast.makeText(
                    this@PreviewListFotoActivity,
                    "Terjadi kesalahan sistem: ${t.message}\nSilakan Coba Lagi",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_KirimFoto){
            loadingDialog.startDialog()
            sendData()
        }
    }

}