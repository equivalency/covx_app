package com.example.cov_x

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class WaitingActivity : AppCompatActivity() {

    private lateinit var imageWait: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)
        supportActionBar?.hide()

        imageWait = findViewById(R.id.waiting_image)

        val animateImage = AnimationUtils.loadAnimation(this, R.anim.rotate)
        imageWait.startAnimation(animateImage)


//        Mainan Statenya disini, sesuai timeout dari retrofit or something

    }
}