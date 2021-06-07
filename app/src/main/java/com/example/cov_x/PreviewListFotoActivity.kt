package com.example.cov_x

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cov_x.adapters.FotoAdapter
import com.example.cov_x.models.FotoDataRequest
import java.io.ByteArrayOutputStream


class PreviewListFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvFoto: RecyclerView
    private lateinit var btnKirimFoto: Button
    private val listFoto = FotoDataRequest()

    companion object {
        const val LIST_FOTO_URI = "list_foto_uri"
        const val LIST_NAMA_FOTO = "list_nama_foto"
    }


    @RequiresApi(Build.VERSION_CODES.P)
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
            val stringBase64 = convertToBase64(imageUri)
            listFoto.files.add(stringBase64)
        }


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

            val stateIntent = Intent(this@PreviewListFotoActivity, StateActivity::class.java)
            stateIntent.putExtra(StateActivity.EXTRA_FOTO, listFoto)
            startActivity(stateIntent)
            finish()
        }
    }

}