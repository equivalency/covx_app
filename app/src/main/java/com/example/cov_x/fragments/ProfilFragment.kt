package com.example.cov_x.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.cov_x.LoginActivity
import com.example.cov_x.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilFragment : Fragment(), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val textNama: TextView = view.findViewById(R.id.textNama)
        val textEmail: TextView = view.findViewById(R.id.textEmail)
        val btnKeluar: Button = view.findViewById(R.id.btnLogout)
        
        btnKeluar.setOnClickListener(this)
        
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnLogout){
//            Clear SharedPreference
//            Go to Login Page

//            Loading bar
//               val builder = AlertDialog.Builder(this)
//               val inflater = LayoutInflater.from(this)
//               builder.setView(inflater.inflate(R.layout.custom_loading, null))
//               builder.setCancelable(false)
//               builder.create().show()

            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
            activity?.finish()
        }
    }


}