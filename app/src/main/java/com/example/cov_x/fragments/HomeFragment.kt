package com.example.cov_x.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.cov_x.PreviewFotoActivity
import com.example.cov_x.PreviewListFotoActivity
import com.example.cov_x.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment() : Fragment(), View.OnClickListener {

    private var thiscontext: Context? = null


    private val PICK_IMAGES_CODE = 111
    private val CAMERA_ACCESS = 112

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thiscontext = container?.context
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageLayout: LinearLayout = view.findViewById(R.id.topLayout)
        val fotoLayout: LinearLayout = view.findViewById(R.id.botLayout)

        imageLayout.setOnClickListener(this)
        fotoLayout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.topLayout){
            requestGaleryPermission()
        }
        else if(v?.id == R.id.botLayout){
            requestCameraPermission()
        }
    }

    private fun intentGaleri(){
        val galeriIntent = Intent()
        galeriIntent.type = "image/*"
        galeriIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        galeriIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(galeriIntent, "Pilih Foto"), PICK_IMAGES_CODE)

    }

    private fun intentCamera(){
        val m_intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg")
        val uri = thiscontext?.let {
            FileProvider.getUriForFile(
                it,
                it.getPackageName().toString() + ".provider",
                file
            )
        }
        m_intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(m_intent, CAMERA_ACCESS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE){
            if (resultCode == Activity.RESULT_OK){
                val listFotoUri: ArrayList<Uri> = arrayListOf()
                if (data?.clipData != null){
                    val jumlahGambar = data.clipData!!.itemCount

                    if (jumlahGambar > 5){
                        Toast.makeText(
                            thiscontext,
                            "Maksimum 5 foto yang dipilih",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        for (i in 0 until jumlahGambar){
                            val imageUri = data.clipData!!.getItemAt(i).uri
                            listFotoUri.add(imageUri)
                        }
                        val previewListFotoIntent = Intent(
                            activity,
                            PreviewListFotoActivity::class.java
                        )
                        previewListFotoIntent.putExtra("listFotoUri", listFotoUri)
                        startActivity(previewListFotoIntent)
                    }
                }
                else{
                    val imageUri = data?.data

                    val previewFotoIntent = Intent(
                        activity,
                        PreviewFotoActivity::class.java
                    )
                    previewFotoIntent.putExtra("FotoUri", imageUri.toString())
                    startActivity(previewFotoIntent)

                }
            }
        }
        else if (requestCode == CAMERA_ACCESS){
            if (resultCode == Activity.RESULT_OK){
                val file = File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg")
                val uri = thiscontext?.let {
                    FileProvider.getUriForFile(
                        it,
                        it.getPackageName().toString() + ".provider",
                        file
                    )
                }

                val previewFotoIntent = Intent(
                    activity,
                    PreviewFotoActivity::class.java
                )
                previewFotoIntent.putExtra("FotoUri", uri.toString())
                startActivity(previewFotoIntent)
            }
        }
    }

    private fun requestGaleryPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
//                Intent here
                intentGaleri()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    thiscontext,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        TedPermission.with(thiscontext)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
            .check();
    }

    private fun requestCameraPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
//                Intent here
                intentCamera()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    thiscontext,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        TedPermission.with(thiscontext)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.CAMERA
            )
            .check();
    }




}