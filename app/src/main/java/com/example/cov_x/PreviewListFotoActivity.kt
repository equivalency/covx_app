package com.example.cov_x

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cov_x.adapters.FotoAdapter
import com.example.cov_x.models.FotoDataRequest
import com.example.cov_x.models.FotoDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class PreviewListFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvFoto: RecyclerView
    private lateinit var btnKirimFoto: Button
    private val listFoto = FotoDataRequest()
    private val loadingDialog = LoadingDialog(this)

    companion object {
        const val LIST_FOTO_URI = "list_foto_uri"
        const val LIST_NAMA_FOTO = "list_nama_foto"
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


        val fotoAdapter = FotoAdapter(listUri, listNamaFoto)
        rvFoto.apply {
            layoutManager = GridLayoutManager(this@PreviewListFotoActivity, 2)
            adapter = fotoAdapter
        }

        for (counter in 0 until listUri.size) {
            val imageUri = listUri.get(counter)
            val byteArray = convertImageToByte(imageUri)
            byteArray?.let { listFoto.files.add(it) }
        }


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
                    Toast.makeText(this@PreviewListFotoActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                    val successIntent = Intent(this@PreviewListFotoActivity, SuccessActivity::class.java)
                    startActivity(successIntent)
                    finish()
                }
                else{
                    loadingDialog.dismissDialog()
                    Toast.makeText(this@PreviewListFotoActivity, "Terjadi kesalahan sistem\n Silakan Coba Lagi", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<FotoDataResponse>, t: Throwable) {
                loadingDialog.dismissDialog()
                Toast.makeText(this@PreviewListFotoActivity, "Terjadi kesalahan sistem: ${t.message}\nSilakan Coba Lagi", Toast.LENGTH_LONG).show()
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