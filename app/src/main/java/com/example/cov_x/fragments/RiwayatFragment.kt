package com.example.cov_x.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.cov_x.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RiwayatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RiwayatFragment : Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleView: RecyclerView = view.findViewById(R.id.rv_Foto)
        val emptyLayout: LinearLayout = view.findViewById(R.id.empty_layout)
        val spinLoader: ProgressBar = view.findViewById(R.id.ProgressBar01)

//        First state
        emptyLayout.setVisibility(View.GONE)
        recycleView.setVisibility(View.GONE)
//        State if no data
        var noData: Boolean = true

//        Implement RecyclerView Here Zulfa hehehe

//        if (noData) {
//            spinLoader.setVisibility(View.GONE)
//            recycleView.setVisibility(View.GONE)
//            emptyLayout.setVisibility(LinearLayout.VISIBLE)
//        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }



}