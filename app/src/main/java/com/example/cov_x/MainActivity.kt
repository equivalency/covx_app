package com.example.cov_x

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cov_x.fragments.HomeFragment
import com.example.cov_x.fragments.RiwayatFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val homeFragment = HomeFragment()
        val riwayatFragment = RiwayatFragment()

//        Still buggy
        if (savedInstanceState == null){
            makeCurrentFragment(homeFragment)
        }

        bottomNav = findViewById(R.id.bottom_navbar)

        bottomNav.setOnItemSelectedListener {
            when (it){
                R.id.nav_home -> makeCurrentFragment(homeFragment)
                R.id.nav_riwayat -> makeCurrentFragment(riwayatFragment)
            }
        }


    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fl_wrapper, fragment)
        commit()
    }
}