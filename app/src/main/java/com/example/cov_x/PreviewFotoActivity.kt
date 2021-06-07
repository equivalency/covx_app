package com.example.cov_x

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cov_x.models.FotoDataRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class PreviewFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnKirim: Button
    private lateinit var imgFoto: ImageView
    private lateinit var textNama: TextView
    private val loadingDialog = LoadingDialog(this)
    private lateinit var filePath: String

    companion object {
        const val FOTO_URI = "foto_uri"
        const val NAMA_FOTO = "nama_foto"
        const val FOTO_PATH = "foto_path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_foto)
        supportActionBar?.hide()

        imgFoto = findViewById(R.id.imageFoto)
        btnKirim = findViewById(R.id.btn_KirimFoto)
        textNama = findViewById(R.id.textNama)

        val stringUri = intent.getStringExtra(FOTO_URI)
        val namaFoto = intent.getStringExtra(NAMA_FOTO)
        filePath = intent.getStringExtra(FOTO_PATH).toString()

        val fotoUri = Uri.parse(stringUri)

        textNama.text = namaFoto
        Glide.with(this)
            .load(fotoUri)
            .apply(RequestOptions())
            .into(imgFoto)


        btnKirim.setOnClickListener(this)

    }

    private fun sendData(){

        val pref = this.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
        val request = ApiClient.buildService(UserService::class.java)
        val token = pref.getString(LoginActivity.KEY_TOKEN, "")
        val stringAuth = "Bearer " + token

        val file = File(filePath)
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        val type = "multipart/form-data"
        builder.addFormDataPart(
            "files[]",
            file.getName(),
            file.asRequestBody(type.toMediaTypeOrNull())
        )
        val requestBody = builder.build()
        Log.d("REQUEST_BODY", requestBody.toString())
        val call = request.uploadImage(stringAuth, requestBody)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful){
                    loadingDialog.dismissDialog()
//                           Save Token
                    Toast.makeText(
                        this@PreviewFotoActivity,
                        "Success " + response.message(),
                        Toast.LENGTH_LONG
                    ).show()
                    val successIntent = Intent(this@PreviewFotoActivity, SuccessActivity::class.java)
                    startActivity(successIntent)
                    finish()
                }
                else{
                    loadingDialog.dismissDialog()
                    Toast.makeText(this@PreviewFotoActivity, "Terjadi kesalahan sistem\n Silakan Coba Lagi", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                loadingDialog.dismissDialog()
                Toast.makeText(this@PreviewFotoActivity, "Terjadi kesalahan sistem: ${t.message}\nSilakan Coba Lagi", Toast.LENGTH_LONG).show()
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