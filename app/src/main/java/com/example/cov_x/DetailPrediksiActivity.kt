package com.example.cov_x

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cov_x.models.ListFotoPrediksiResponse

class DetailPrediksiActivity : AppCompatActivity() {

    companion object {
        const val FOTO_DATA = "foto_data"
    }

    private lateinit var fotoParu: ImageView
    private lateinit var backButton: ImageButton
    private lateinit var textStatus: TextView
    private lateinit var textPersentase: TextView
    private lateinit var textNamaFoto: TextView
    private lateinit var textTanggal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_prediksi)
        supportActionBar?.hide()

        fotoParu = findViewById(R.id.imageParu)
        backButton = findViewById(R.id.imageButtonBack)
        textStatus = findViewById(R.id.textStatus)
        textPersentase = findViewById(R.id.text_persentase)
        textNamaFoto = findViewById(R.id.text_NamaFoto)
        textTanggal = findViewById(R.id.text_tanggal)

        val prediksiData = intent.getParcelableExtra<ListFotoPrediksiResponse>(FOTO_DATA) as ListFotoPrediksiResponse

        Glide.with(this)
                .load(prediksiData.filepath)
                .apply(RequestOptions())
                .into(fotoParu)
        backButton.setOnClickListener {
            finish()
        }
        textStatus.text = prediksiData.status
        textPersentase.text = prediksiData.prediction
        textNamaFoto.text = prediksiData.filename
        textTanggal.text = prediksiData.created_at

    }
}