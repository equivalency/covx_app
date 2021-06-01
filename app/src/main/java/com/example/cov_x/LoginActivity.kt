package com.example.cov_x

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

   private lateinit var textDaftar: TextView
   private lateinit var btnLogin: Button
   private lateinit var editEmail: EditText
   private lateinit var editPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        supportActionBar?.hide()

        textDaftar = findViewById(R.id.txtDaftar)
        btnLogin = findViewById(R.id.btnLogin)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)

        textDaftar.setOnClickListener(this)
        btnLogin.setOnClickListener(this)

    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onClick(v: View?) {
       if (v?.id == R.id.txtDaftar){
           val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
           startActivity(registerIntent)
       }
       else if (v?.id == R.id.btnLogin){
           val email = editEmail.text.toString().trim()
           val password = editPassword.text.toString().trim()

           var isEmptyField = false

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

//               Loading bar
//               val builder = AlertDialog.Builder(this)
//               val inflater = LayoutInflater.from(this)
//               builder.setView(inflater.inflate(R.layout.custom_loading, null))
//               builder.setCancelable(false)
//               builder.create().show()

               val homeIntent = Intent(this@LoginActivity, MainActivity::class.java)
               startActivity(homeIntent)
               finish()
//
//               Toast.makeText(this, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
           }

       }
    }

}