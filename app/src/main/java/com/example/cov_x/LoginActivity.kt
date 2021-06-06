package com.example.cov_x

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cov_x.models.LoginRequest
import com.example.cov_x.models.LoginResponse
import com.example.cov_x.models.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

   private lateinit var textDaftar: TextView
   private lateinit var btnLogin: Button
   private lateinit var editEmail: EditText
   private lateinit var editPassword: EditText

   companion object {
       const val PREF_NAME = "LOGIN_PREF"
       const val KEY_ID = "key_id"
       const val KEY_EMAIL = "key_email"
       const val KEY_NAME = "key_name"
       const val KEY_TOKEN = "key_token"
       const val EXTRA_USER = "extra_user"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        supportActionBar?.hide()

        textDaftar = findViewById(R.id.txtDaftar)
        btnLogin = findViewById(R.id.btnLogin)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)

        val registerData: RegisterRequest? = intent.getParcelableExtra(EXTRA_USER)

        editEmail.setText(registerData?.email)
        editPassword.setText(registerData?.password)


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

    private fun saveLoginData(loginData: LoginResponse){
        val sharedPreference =  getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putInt(KEY_ID, loginData.id)
        editor.putString(KEY_NAME, loginData.name)
        editor.putString(KEY_EMAIL, loginData.email)
        editor.putString(KEY_TOKEN, loginData.token)
        editor.apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
       if (v?.id == R.id.txtDaftar){
           val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
           startActivity(registerIntent)
       }
       else if (v?.id == R.id.btnLogin){
           val email = editEmail.text.toString()
           val password = editPassword.text.toString()

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


               val loadingDialog = LoadingDialog(this)
               loadingDialog.startDialog()
               val loginData = LoginRequest(email, password)

               val request = ApiClient.buildService(UserService::class.java)
               val base = email + ":" + password
               val stringAuth = "Basic " + Base64.getEncoder().encodeToString(base.toByteArray())
               val call = request.loginUser(stringAuth,loginData)

               call.enqueue(object: Callback<LoginResponse> {
                   override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                       if (response.isSuccessful){
                           loadingDialog.dismissDialog()
//                           Save Token
                           response.body()?.let { saveLoginData(it) }

                           Toast.makeText(this@LoginActivity, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
                           val homeIntent = Intent(this@LoginActivity, MainActivity::class.java)
                           startActivity(homeIntent)
                           finish()
                       }
                       else{
                           loadingDialog.dismissDialog()
                           Toast.makeText(this@LoginActivity, "Email atau Password tidak cocok\nSilahkan Coba Lagi", Toast.LENGTH_LONG).show()
                       }
                   }

                   override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                       loadingDialog.dismissDialog()
                       Toast.makeText(this@LoginActivity, "Terjadi kesalahan sistem: ${t.message}\nSilahkan Coba Lagi", Toast.LENGTH_LONG).show()
                   }

               })

           }

       }
    }


}