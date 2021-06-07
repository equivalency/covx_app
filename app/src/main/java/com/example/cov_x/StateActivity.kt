package com.example.cov_x

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.cov_x.models.FotoDataRequest
import com.example.cov_x.models.FotoDataResponse
import com.example.cov_x.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class StateActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imageWait: ImageView
    private lateinit var waitLayout: LinearLayout
    private lateinit var successLayout: LinearLayout
    private lateinit var failLayout: LinearLayout
    
    private lateinit var btnCobaLagi: Button
    private lateinit var btnBatal: Button
    private lateinit var btnLihatHasil: Button

    companion object {
        const val EXTRA_FOTO = "extra_foto"
        const val SUCCESS_STATE = "success_state"
    }

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state)

        supportActionBar?.hide()

        waitLayout = findViewById(R.id.waitingLayout)
        successLayout = findViewById(R.id.successLayout)
        failLayout = findViewById(R.id.failLayout)
        
        btnCobaLagi = findViewById(R.id.btnCobaLagi)
        btnBatal = findViewById(R.id.btnBatal)
        btnLihatHasil = findViewById(R.id.btnLihatHasil)

        imageWait = findViewById(R.id.waiting_image)
        if(waitLayout.visibility == View.VISIBLE){
            val animateImage = AnimationUtils.loadAnimation(this, R.anim.rotate)
            imageWait.startAnimation(animateImage)
        }

        btnCobaLagi.setOnClickListener(this)
        btnBatal.setOnClickListener(this)
        btnLihatHasil.setOnClickListener(this)

        sendData()

    }

    private fun sendData(){
        val fotoData = intent.getParcelableExtra<FotoDataRequest>(EXTRA_FOTO) as FotoDataRequest
        val pref = this.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)

        val request = ApiClient.buildService(UserService::class.java)
        val token = pref.getString(LoginActivity.KEY_TOKEN, "")
        val stringAuth = "Bearer " + token
        val call = request.uploadFoto(stringAuth, fotoData)

        call.enqueue(object: Callback<FotoDataResponse> {
            override fun onResponse(call: Call<FotoDataResponse>, response: Response<FotoDataResponse>){
                if (response.isSuccessful){

                    Toast.makeText(this@StateActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    successLayout.visibility = View.VISIBLE
                    waitLayout.visibility = View.GONE

                }
                else{
                    Toast.makeText(this@StateActivity, "Terjadi kesalahan sistem\n Silakan Coba Lagi", Toast.LENGTH_LONG).show()
                    waitLayout.visibility = View.GONE
                    failLayout.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<FotoDataResponse>, t: Throwable) {
                Toast.makeText(this@StateActivity, "Terjadi kesalahan sistem: ${t.message}\nSilakan Coba Lagi", Toast.LENGTH_LONG).show()
                waitLayout.visibility = View.GONE
                failLayout.visibility = View.VISIBLE
            }

        })
    }

    private fun lihatHasil(){
        val riwayatIntent = Intent(this@StateActivity, MainActivity::class.java)
        riwayatIntent.putExtra(SUCCESS_STATE, true)
        startActivity(riwayatIntent)
        finish()
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLihatHasil -> lihatHasil()
            R.id.btnBatal -> finish()
            R.id.btnCobaLagi -> sendData()
        }
    }
}