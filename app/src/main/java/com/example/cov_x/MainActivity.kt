package com.example.cov_x

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cov_x.fragments.HomeFragment
import com.example.cov_x.fragments.ProfilFragment
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
        val profilFragment = ProfilFragment()

        bottomNav = findViewById(R.id.bottom_navbar)

        val isSuccessState = intent.getBooleanExtra(SuccessActivity.SUCCESS_STATE, false)
        if (isSuccessState){
            bottomNav.setItemSelected(R.id.nav_riwayat, true)
            makeCurrentFragment(riwayatFragment)
        }
        else {
            bottomNav.setItemSelected(R.id.nav_home, true)
            makeCurrentFragment(homeFragment)
        }

        bottomNav.setOnItemSelectedListener {
            when (it){
                R.id.nav_home -> makeCurrentFragment(homeFragment)
                R.id.nav_riwayat -> makeCurrentFragment(riwayatFragment)
                R.id.nav_profil -> makeCurrentFragment(profilFragment)

            }
        }


    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
//        setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_right_to_left, R.anim.exit_left_to_right)
        replace(R.id.fl_wrapper, fragment)
        commit()
    }
}