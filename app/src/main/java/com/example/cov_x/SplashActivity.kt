package com.example.cov_x

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val sharedPreference =  getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPreference.contains(LoginActivity.KEY_TOKEN)){
                val homeIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(homeIntent)
                finish()
            }
            else {
                val loginIntent = Intent( this@SplashActivity, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
        }, 2000)
    }
}