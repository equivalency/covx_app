package com.example.cov_x.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cov_x.LoginActivity
import com.example.cov_x.R


/**
 * A simple [Fragment] subclass.
 * Use the [ProfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilFragment : Fragment(), View.OnClickListener {
    private var thiscontext: Context? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pref = activity!!.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
        val textNama: TextView = view.findViewById(R.id.textNama)
        val textEmail: TextView = view.findViewById(R.id.textEmail)
        val btnKeluar: Button = view.findViewById(R.id.btnLogout)

        textNama.text = pref.getString(LoginActivity.KEY_NAME, R.string.nama.toString())
        textEmail.text = pref.getString(LoginActivity.KEY_EMAIL, R.string.email.toString())

        btnKeluar.setOnClickListener(this)
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thiscontext = container?.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnLogout){
//            Clear SharedPreference
            val pref = activity!!.getSharedPreferences(LoginActivity.PREF_NAME, Context.MODE_PRIVATE)
            pref.edit().clear().apply()

            Toast.makeText(thiscontext, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
//            Go to Login Page
            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
            activity?.finish()
        }
    }


}