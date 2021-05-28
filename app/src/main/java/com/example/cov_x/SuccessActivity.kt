package com.example.cov_x

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cov_x.fragments.HomeFragment
import com.example.cov_x.fragments.RiwayatFragment

class SuccessActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnLihatHasil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        supportActionBar?.hide()
        
        btnLihatHasil = findViewById(R.id.btnLihatHasil)

        btnLihatHasil.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnLihatHasil){
            val riwayatIntent = Intent(this@SuccessActivity, MainActivity::class.java)
            riwayatIntent.putExtra("successState", true)
            startActivity(riwayatIntent)
            finish()
        }
    }

}