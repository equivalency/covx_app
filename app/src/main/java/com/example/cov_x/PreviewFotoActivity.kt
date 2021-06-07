package com.example.cov_x

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cov_x.models.FotoDataRequest
import com.example.cov_x.models.FotoDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream


class PreviewFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnKirim: Button
    private lateinit var imgFoto: ImageView
    private lateinit var textNama: TextView
    private val listFoto = FotoDataRequest()
    private val loadingDialog = LoadingDialog(this)

    companion object {
        const val FOTO_URI = "foto_uri"
        const val NAMA_FOTO = "nama_foto"
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_foto)
        supportActionBar?.hide()

        imgFoto = findViewById(R.id.imageFoto)
        btnKirim = findViewById(R.id.btn_KirimFoto)
        textNama = findViewById(R.id.textNama)

        val stringUri = intent.getStringExtra(FOTO_URI)
        val namaFoto = intent.getStringExtra(NAMA_FOTO)

        val fotoUri = Uri.parse(stringUri)
        val byteArray = convertImageToByte(fotoUri)

        byteArray?.let { listFoto.files.add(it) }

        textNama.text = namaFoto
        Glide.with(this)
            .load(fotoUri)
            .apply(RequestOptions())
            .into(imgFoto)


        btnKirim.setOnClickListener(this)

    }

    private fun convertImageToByte(uri: Uri?): ByteArray? {
        var data: ByteArray? = null
        try {
            val cr = baseContext.contentResolver
            val inputStream = cr.openInputStream(uri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            data = baos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return data
    }

    private fun sendData(){

        val pref = this.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
        val request = ApiClient.buildService(UserService::class.java)
        val token = pref.getString(LoginActivity.KEY_TOKEN, "")
        val stringAuth = "Bearer " + token
        val call = request.uploadFoto(stringAuth, listFoto)

        call.enqueue(object: Callback<FotoDataResponse> {
            override fun onResponse(call: Call<FotoDataResponse>, response: Response<FotoDataResponse>){
                if (response.isSuccessful){
                    loadingDialog.dismissDialog()
                    Toast.makeText(this@PreviewFotoActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                    val successIntent = Intent(this@PreviewFotoActivity, SuccessActivity::class.java)
                    startActivity(successIntent)
                    finish()
                }
                else{
                    loadingDialog.dismissDialog()
                    Toast.makeText(this@PreviewFotoActivity, "Terjadi kesalahan sistem\n Silakan Coba Lagi", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<FotoDataResponse>, t: Throwable) {
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