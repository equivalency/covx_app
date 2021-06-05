package com.example.cov_x

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.cov_x.models.RegisterRequest
import com.example.cov_x.models.RegisterResponse
import retrofit2.Callback
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val BASE_URL = "http://35.192.163.245/"
        private val TAG = RegisterActivity::class.java.simpleName
    }

    private lateinit var editNama: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnDaftar: Button
    private lateinit var txtLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        editNama = findViewById(R.id.editNama)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnDaftar = findViewById(R.id.btnDaftar)
        txtLogin = findViewById(R.id.txtLogin)

        btnDaftar.setOnClickListener(this)
        txtLogin.setOnClickListener(this)

        supportActionBar?.hide()


    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.txtLogin){
            val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        else if (v?.id == R.id.btnDaftar){
            val nama = editNama.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            var isEmptyField = false

            if (nama.isEmpty()){
                isEmptyField = true
                editNama.error = "Field nama tidak boleh kosong"
            }
            if (email.isEmpty()){
                isEmptyField = true
                editEmail.error = "Field email tidak boleh kosong"
            }
            if (password.isEmpty()){
                isEmptyField = true
                editPassword.error = "Field password tidak boleh kosong"
            }

            if(!isEmptyField){
                closeKeyboard()

                val builder = AlertDialog.Builder(this)
                val inflater = LayoutInflater.from(this)
                builder.setView(inflater.inflate(R.layout.custom_loading, null))
                builder.setCancelable(false)
                builder.create().show()

                val registerData = RegisterRequest()
                registerData.name = nama
                registerData.email = email
                registerData.password = password

                val request = ApiClient.buildService(UserService::class.java)
                val call = request.registerUser(registerData)
                
                call.enqueue(object: Callback<RegisterResponse>{
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>){
                        if (response.isSuccessful){
                            builder.create().dismiss()
                            Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                            val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(loginIntent)
                        }
                        else{
                            Toast.makeText(this@RegisterActivity, "Terjadi kesalahan sistem", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        builder.create().dismiss()
                        Toast.makeText(this@RegisterActivity, "${t.message}", Toast.LENGTH_LONG).show()
                    }
                })

//                Loading bar
//                val builder = AlertDialog.Builder(this)
//                val inflater = LayoutInflater.from(this)
//                builder.setView(inflater.inflate(R.layout.custom_loading, null))
//                builder.setCancelable(false)
//                builder.create().show()

//                val homeIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
//                startActivity(homeIntent)
//
//                Toast.makeText(this, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
            }
        }
    }

}