package com.example.cov_x

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PreviewFotoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnKirim: Button
    private lateinit var imgFoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_foto)
        supportActionBar?.hide()

        imgFoto = findViewById(R.id.imageFoto)
        btnKirim = findViewById(R.id.btn_KirimFoto)

        val stringUri = intent.getStringExtra("FotoUri")
        val fotoUri = Uri.parse(stringUri)

        Glide.with(this)
            .load(fotoUri)
            .apply(RequestOptions())
            .into(imgFoto)

        btnKirim.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_KirimFoto){

//            SEND IMAGE LOGIC

            val waitingIntent = Intent(this@PreviewFotoActivity, WaitingActivity::class.java)
            startActivity(waitingIntent)
            finish()
        }
    }
}