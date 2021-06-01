package com.example.cov_x

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

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
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

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

//                Loading bar
                val builder = AlertDialog.Builder(this)
                val inflater = LayoutInflater.from(this)
                builder.setView(inflater.inflate(R.layout.custom_loading, null))
                builder.setCancelable(false)
                builder.create().show()

//                val homeIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
//                startActivity(homeIntent)
//
//                Toast.makeText(this, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
            }
        }
    }

}