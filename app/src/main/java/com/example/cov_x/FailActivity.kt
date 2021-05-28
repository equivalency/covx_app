package com.example.cov_x

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cov_x.fragments.HomeFragment

class FailActivity : AppCompatActivity(), View.OnClickListener {
    private var btnCobaLagi: Button = findViewById(R.id.btnCobaLagi)
    private var btnBatal: Button = findViewById(R.id.btnBatal)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fail)
        supportActionBar?.hide()

        btnCobaLagi.setOnClickListener(this)
        btnBatal.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnCobaLagi) {
            val waitingIntent = Intent(this@FailActivity, WaitingActivity::class.java)
            startActivity(waitingIntent)
            finish()
        }
        else if(v?.id == R.id.btnBatal) {
            val homeFragment = HomeFragment()
            makeCurrentFragment(homeFragment)
            finish()
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fl_wrapper, fragment)
        commit()
    }
}