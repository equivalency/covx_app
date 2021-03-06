package com.example.cov_x

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class SuccessActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnLihatHasil: Button

    companion object {
        const val SUCCESS_STATE = "success_state"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        supportActionBar?.title = "Laman Sukses"

        btnLihatHasil = findViewById(R.id.btnLihatHasil)

        btnLihatHasil.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btnLihatHasil){
            val riwayatIntent = Intent(this@SuccessActivity, MainActivity::class.java)
            riwayatIntent.putExtra(SUCCESS_STATE, true)
            startActivity(riwayatIntent)
            finish()
        }
    }
}