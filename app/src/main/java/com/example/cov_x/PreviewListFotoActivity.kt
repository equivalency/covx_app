package com.example.cov_x

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cov_x.adapters.FotoAdapter


class PreviewListFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvFoto: RecyclerView
    private lateinit var btnKirimFoto: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_list_foto)
        supportActionBar?.hide()

        rvFoto = findViewById(R.id.rv_fotoTampil)
        btnKirimFoto = findViewById(R.id.btn_KirimFoto)

        btnKirimFoto.setOnClickListener(this)


        rvFoto.setHasFixedSize(true)

        val listUri = intent.getSerializableExtra("listFotoUri") as ArrayList<Uri>


        val fotoAdapter = FotoAdapter(listUri)
        rvFoto.apply {
            layoutManager = GridLayoutManager(this@PreviewListFotoActivity, 2)
            adapter = fotoAdapter
        }

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_KirimFoto){
//            SEND FOTO LOGIC HERE ZULFA
            val waitingIntent = Intent(this@PreviewListFotoActivity, WaitingActivity::class.java)
            startActivity(waitingIntent)
            finish()
//            val successIntent = Intent(this@PreviewListFotoActivity, SuccessActivity::class.java)
//            startActivity(successIntent)
//            finish()
        }
    }

}