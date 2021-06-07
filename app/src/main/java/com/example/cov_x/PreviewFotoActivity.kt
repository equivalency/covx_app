package com.example.cov_x

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cov_x.models.FotoDataRequest
import java.io.ByteArrayOutputStream

class PreviewFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnKirim: Button
    private lateinit var imgFoto: ImageView
    private lateinit var textNama: TextView
    private val listFoto = FotoDataRequest()

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
        val stringBase64 = convertToBase64(fotoUri)
        listFoto.files.add(stringBase64)

        textNama.text = namaFoto
        Glide.with(this)
            .load(fotoUri)
            .apply(RequestOptions())
            .into(imgFoto)


        btnKirim.setOnClickListener(this)

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun convertToBase64(uri: Uri): String {
        val source = ImageDecoder.createSource(this.contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)

        return encoded
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_KirimFoto){

//            SEND IMAGE LOGIC


            val stateIntent = Intent(this@PreviewFotoActivity, StateActivity::class.java)
            stateIntent.putExtra(StateActivity.EXTRA_FOTO, listFoto)
            startActivity(stateIntent)
            finish()
        }
    }
}